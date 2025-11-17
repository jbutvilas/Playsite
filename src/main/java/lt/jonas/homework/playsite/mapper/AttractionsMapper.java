package lt.jonas.homework.playsite.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lt.jonas.homework.playsite.model.dto.AttractionDto;
import lt.jonas.homework.playsite.model.entity.Attraction;
import lt.jonas.homework.playsite.model.entity.PlaySite;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AttractionsMapper {

    public static Attraction toEntity(AttractionDto attractionDto, PlaySite playSite) {
        return new Attraction(
                null,
                attractionDto.name(),
                attractionDto.maxCapacity(),
                playSite
        );
    }

    public static List<Attraction> toEntityList(List<AttractionDto> attractionDtos, PlaySite playSite) {
        return attractionDtos.stream()
                .map(attractionDto -> toEntity(attractionDto, playSite))
                .toList();
    }
}
