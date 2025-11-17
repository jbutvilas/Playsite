package lt.jonas.homework.playsite.model.api.response;

import java.time.ZonedDateTime;

import static lt.jonas.homework.playsite.utils.DateTimeUtils.getUtcTimestamp;

public record ErrorResponseDto(
        String message,
        String errorCode,
        ZonedDateTime timestamp
) {

    public ErrorResponseDto(String message, String errorCode) {
        this(message, errorCode, getUtcTimestamp());
    }
}
