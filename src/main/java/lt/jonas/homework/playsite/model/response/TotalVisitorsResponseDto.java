package lt.jonas.homework.playsite.model.response;

import java.time.ZonedDateTime;

import static lt.jonas.homework.playsite.utils.DateTimeUtils.getUtcTimestamp;

public record TotalVisitorsResponseDto(
        Integer numberOfPlaySites,
        Integer totalVisitorCount,
        ZonedDateTime timestamp
) {

    public TotalVisitorsResponseDto(int numberOfPlaySites, int totalVisitorCount) {
        this(numberOfPlaySites, totalVisitorCount, getUtcTimestamp());
    }
}
