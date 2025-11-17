package lt.jonas.homework.playsite.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record AttractionDto(
        @NotBlank(message = "Name cannot be blank")
        String name,

        @Min(value = 1, message = "Maximum capacity must be at least 1")
        int maxCapacity
) {}
