package lt.jonas.homework.playsite.model.api.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lt.jonas.homework.playsite.model.dto.AttractionDto;

import java.util.List;

public record PlaySiteRequest(
        @NotBlank(message = "PlaySite name cannot be blank")
        String name,

        @Valid
        @NotEmpty(message = "PlaySite must have at least one attraction")
        List<AttractionDto> attractions
) {}
