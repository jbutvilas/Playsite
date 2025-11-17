package lt.jonas.homework.playsite.service;

import lombok.RequiredArgsConstructor;
import lt.jonas.homework.playsite.exception.model.PlaySiteOutOfCapacityException;
import lt.jonas.homework.playsite.exception.model.ResourceNotFoundException;
import lt.jonas.homework.playsite.exception.model.TicketUsedException;
import lt.jonas.homework.playsite.mapper.AttractionsMapper;
import lt.jonas.homework.playsite.mapper.KidsMapper;
import lt.jonas.homework.playsite.model.api.request.KidRequest;
import lt.jonas.homework.playsite.model.api.request.PlaySiteRequest;
import lt.jonas.homework.playsite.model.api.response.TotalVisitorsResponseDto;
import lt.jonas.homework.playsite.model.api.response.UtilisationResponseDto;
import lt.jonas.homework.playsite.model.entity.Kid;
import lt.jonas.homework.playsite.model.entity.PlaySite;
import lt.jonas.homework.playsite.repository.KidRepository;
import lt.jonas.homework.playsite.repository.PlaySiteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class PlaySiteServiceImpl implements PlaySiteService{

    private final PlaySiteRepository playSiteRepository;
    private final KidRepository kidRepository;

    public List<PlaySite> getAllPlaySites() {
        return playSiteRepository.findAll();
    }

    public Optional<PlaySite> getPlaySiteById(Long id) {
        return playSiteRepository.findById(id);
    }

    public PlaySite createPlaySite(PlaySiteRequest playSiteRequest) {
        PlaySite playSite = new PlaySite();
        playSite.setName(playSiteRequest.name());
        playSite.getAttractions().addAll(AttractionsMapper.toEntityList(playSiteRequest.attractions(), playSite));
        return playSiteRepository.save(playSite);
    }

    public PlaySite updatePlaySite(Long id, PlaySiteRequest playSiteRequest) {
        PlaySite playSite = getPlaySite(id);
        playSite.getAttractions().clear();
        playSite.getAttractions().addAll(AttractionsMapper.toEntityList(playSiteRequest.attractions(), playSite));
        playSite.setName(playSiteRequest.name());

        tryAddingKidsFromQueue(playSite);

        return playSiteRepository.save(playSite);
    }

    public void deletePlaySite(Long id) {
        playSiteRepository.deleteById(id);
    }

    public Kid addKidToPlaySite(Long playSiteId, KidRequest kidRequest, boolean acceptQueue) {
        var playSite = getPlaySite(playSiteId);
        if (kidRepository.existsById(kidRequest.ticketNumber())) {
            throw new TicketUsedException();
        }

        var kid = KidsMapper.toEntity(kidRequest);
        if (playSite.addKid(kid)) {
            playSiteRepository.save(playSite);
        } else if (acceptQueue) {
            playSite.getQueue().add(kid.getTicketNumber());
            kidRepository.save(kid);
        } else {
            var message = "Playsite is at full capacity of %s kids and cannot accept any more at the moment".formatted(playSite.getTotalCapacity());
            throw new PlaySiteOutOfCapacityException(message);
        }
        return kid;
    }

    public void removeKidFromPlaySite(Long playSiteId, Long ticketNumber) {
        var playSite = getPlaySite(playSiteId);
        var kid = getKid(ticketNumber);

        if (playSite.getKids().contains(kid)) {
            playSite.removeKid(kid);
            getNextKidInQueue(playSite).ifPresent(playSite::addKid);
        } else if (playSite.getQueue().contains(ticketNumber)) {
            playSite.getQueue().remove(ticketNumber);
            kidRepository.delete(kid);
        } else {
            throw new IllegalStateException("Kid not found in play site or queue");
        }
        playSiteRepository.save(playSite);
    }

    public TotalVisitorsResponseDto getTotalVisitorCountForToday() {
        var playSites = playSiteRepository.findAll();
        var totalVisitorCount = playSites.stream().mapToInt(PlaySite::getDailyVisitorCount).sum();
        return new TotalVisitorsResponseDto(playSites.size(), totalVisitorCount);
    }

    public UtilisationResponseDto getPlaySiteUtilisation(Long playSiteId) {
        PlaySite playSite = getPlaySite(playSiteId);
        double utilisation = playSite.getTotalCapacity() == 0 ? 0 : (double) playSite.getKids().size() / playSite.getTotalCapacity() * 100;
        return new UtilisationResponseDto(playSiteId, utilisation);
    }

    /**
     * Attempts to add kids from the queue to the play site if there is available capacity.
     *
     * @param playSite the play site
     */
    private void tryAddingKidsFromQueue(PlaySite playSite) {
        var newCapacity = playSite.getTotalCapacity();
        var currentKidCount = playSite.getKids().size();
        if (newCapacity < currentKidCount) {
            throw new PlaySiteOutOfCapacityException("Unable to update play site as new capacity is smaller than current kid count");
        }

        int availableCapacity = newCapacity - currentKidCount;

        for (int i = 0; i < availableCapacity; i++) {
            var optionalKid = getNextKidInQueue(playSite);
            if (optionalKid.isEmpty()) {
                break;
            }

            playSite.addKid(optionalKid.get());
        }
    }

    /**
     * Retrieves the next kid in the queue for a play site.
     *
     * @param playSite the play site
     * @return an optional containing the next kid in the queue, if present
     */
    private Optional<Kid> getNextKidInQueue(PlaySite playSite) {
        return dequeueNext(playSite).map(this::getKid);
    }

    /**
     * Dequeues the next ticket number from the play site's queue.
     *
     * @param playSite the play site
     * @return an optional containing the next ticket number, if present
     */
    private Optional<Long> dequeueNext(PlaySite playSite) {
        var queue = playSite.getQueue();
        if (!queue.isEmpty()) {
            var nextTicket = queue.iterator().next();
            queue.remove(nextTicket);
            return Optional.of(nextTicket);
        }
        return Optional.empty();
    }

    /**
     * Retrieves a play site by its ID or throws an exception if not found.
     *
     * @param playSiteId the ID of the play site
     * @return the play site
     * @throws ResourceNotFoundException if the play site is not found
     */
    private PlaySite getPlaySite(Long playSiteId) {
        return playSiteRepository.findById(playSiteId)
                .orElseThrow(() -> new ResourceNotFoundException("Playsite by id: %s not found".formatted(playSiteId)));
    }

    /**
     * Retrieves a kid by their ticket number or throws an exception if not found.
     *
     * @param ticketNumber the ticket number of the kid
     * @return the kid
     * @throws ResourceNotFoundException if the kid is not found
     */
    private Kid getKid(Long ticketNumber) {
        return kidRepository.findById(ticketNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Kid by ticketNumber: %s not found".formatted(ticketNumber)));
    }
}
