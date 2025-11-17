package lt.jonas.homework.playsite.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lt.jonas.homework.playsite.model.api.request.KidRequest;
import lt.jonas.homework.playsite.model.api.request.PlaySiteRequest;
import lt.jonas.homework.playsite.model.api.response.TotalVisitorsResponseDto;
import lt.jonas.homework.playsite.model.api.response.UtilisationResponseDto;
import lt.jonas.homework.playsite.model.entity.Kid;
import lt.jonas.homework.playsite.model.entity.PlaySite;
import lt.jonas.homework.playsite.service.PlaySiteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/playsites")
@RequiredArgsConstructor
public class PlaySiteController {

    private final PlaySiteService playSiteService;

    @GetMapping
    public List<PlaySite> getAllPlaySites() {
        return playSiteService.getAllPlaySites();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlaySite> getPlaySiteById(@PathVariable Long id) {
        return playSiteService.getPlaySiteById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PlaySite createPlaySite(@Valid @RequestBody PlaySiteRequest playSiteRequest) {
        return playSiteService.createPlaySite(playSiteRequest);
    }

    @PutMapping("/{id}")
    public PlaySite updatePlaySite(@PathVariable Long id, @Valid @RequestBody PlaySiteRequest playSiteRequest) {
        return playSiteService.updatePlaySite(id, playSiteRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePlaySite(@PathVariable Long id) {
        playSiteService.deletePlaySite(id);
    }

    @PostMapping("/{id}/kids")
    @ResponseStatus(HttpStatus.CREATED)
    public Kid addKidToPlaySite(@PathVariable Long id, @Valid @RequestBody KidRequest kidRequest, @RequestParam boolean acceptQueue) {
        return playSiteService.addKidToPlaySite(id, kidRequest, acceptQueue);
    }

    @DeleteMapping("/{id}/kids/{ticketNumber}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeKidFromPlaySiteOrQueue(@PathVariable Long id, @PathVariable Long ticketNumber) {
        playSiteService.removeKidFromPlaySite(id, ticketNumber);
    }

    @GetMapping("/{id}/utilisation")
    public UtilisationResponseDto getPlaySiteUtilisation(@PathVariable Long id) {
        return playSiteService.getPlaySiteUtilisation(id);
    }

    @GetMapping("/total-visitors")
    public TotalVisitorsResponseDto getTotalVisitorCountForToday() {
        return playSiteService.getTotalVisitorCountForToday();
    }
}
