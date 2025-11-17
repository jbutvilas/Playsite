package lt.jonas.homework.playsite.service;

import lt.jonas.homework.playsite.model.api.request.KidRequest;
import lt.jonas.homework.playsite.model.api.request.PlaySiteRequest;
import lt.jonas.homework.playsite.model.api.response.TotalVisitorsResponseDto;
import lt.jonas.homework.playsite.model.api.response.UtilisationResponseDto;
import lt.jonas.homework.playsite.model.entity.Kid;
import lt.jonas.homework.playsite.model.entity.PlaySite;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing play sites and their associated operations.
 */
public interface PlaySiteService {

    /**
     * Retrieves all play sites.
     *
     * @return a list of all play sites
     */
    List<PlaySite> getAllPlaySites();

    /**
     * Retrieves a play site by its ID.
     *
     * @param id the ID of the play site
     * @return an optional containing the play site if found
     */
    Optional<PlaySite> getPlaySiteById(Long id);

    /**
     * Creates a new play site based on the request.
     *
     * @param playSiteRequest the request containing play site details
     * @return the created play site
     */
    PlaySite createPlaySite(PlaySiteRequest playSiteRequest);

    /**
     * Updates an existing play site by its ID.
     *
     * @param id the ID of the play site
     * @param playSiteRequest the request containing updated play site details
     * @return the updated play site
     */
    PlaySite updatePlaySite(Long id, PlaySiteRequest playSiteRequest);

    /**
     * Deletes a play site by its ID.
     *
     * @param id the ID of the play site
     */
    void deletePlaySite(Long id);

    /**
     * Adds a kid to a play site. If the play site is full and queueing is accepted, the kid is added to the queue.
     *
     * @param playSiteId the ID of the play site
     * @param kidRequest the request containing kid details
     * @param acceptQueue whether to add the kid to the queue if the play site is full
     * @return the added kid
     */
    Kid addKidToPlaySite(Long playSiteId, KidRequest kidRequest, boolean acceptQueue);

    /**
     * Removes a kid from the play site or its queue.
     *
     * @param playSiteId the ID of the play site
     * @param ticketNumber the ticket number of the kid
     */
    void removeKidFromPlaySite(Long playSiteId, Long ticketNumber);

    /**
     * Retrieves the total visitor count for all play sites for today.
     *
     * @return a DTO containing the total visitor count
     */
    TotalVisitorsResponseDto getTotalVisitorCountForToday();

    /**
     * Retrieves the utilisation percentage of a play site.
     *
     * @param playSiteId the ID of the play site
     * @return a DTO containing the utilisation percentage
     */
    UtilisationResponseDto getPlaySiteUtilisation(Long playSiteId);
}
