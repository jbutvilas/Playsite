package lt.jonas.homework.playsite.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Kid {

    @Id
    @EqualsAndHashCode.Include
    private Long ticketNumber;

    private String name;

    private int age;

    @ManyToOne
    @JsonIgnore
    @ToString.Exclude
    private PlaySite playSite;
}
