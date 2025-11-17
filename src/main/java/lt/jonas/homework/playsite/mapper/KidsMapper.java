package lt.jonas.homework.playsite.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lt.jonas.homework.playsite.model.api.request.KidRequest;
import lt.jonas.homework.playsite.model.entity.Kid;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class KidsMapper {

    public static Kid toEntity(KidRequest kidRequest) {
        return new Kid(
                kidRequest.ticketNumber(),
                kidRequest.name(),
                kidRequest.age(),
                null
        );
    }
}
