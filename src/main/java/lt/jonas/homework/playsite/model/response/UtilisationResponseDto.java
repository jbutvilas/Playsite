package lt.jonas.homework.playsite.model.response;

import java.time.ZonedDateTime;

import static lt.jonas.homework.playsite.utils.DateTimeUtils.getUtcTimestamp;

public record UtilisationResponseDto(
        Long playSiteId,
        Double utilisation,
        ZonedDateTime timestamp
) {

    public UtilisationResponseDto(long playSiteId, double utilisation) {
        this(playSiteId, utilisation, getUtcTimestamp());
    }
}
