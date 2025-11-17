package lt.jonas.homework.playsite.model.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PlaySite {

    @Id
    @ToString.Include
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @ToString.Include
    private String name;

    @Getter
    @OneToMany(mappedBy = "playSite", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Attraction> attractions = new ArrayList<>();

    @OneToMany(mappedBy = "playSite", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Kid> kids = new HashSet<>();

    @Getter
    @ElementCollection
    private Set<Long> queue = new LinkedHashSet<>();

    @Getter
    private int dailyVisitorCount = 0;

    private LocalDate lastResetDate = LocalDate.now();

    public int getTotalCapacity() {
        return attractions.stream().mapToInt(Attraction::getMaxCapacity).sum();
    }

    public boolean addKid(Kid kid) {
        resetDailyVisitorCountIfNewDay();
        var totalCapacity = getTotalCapacity();
        if (kids.size() < totalCapacity) {
            dailyVisitorCount++;
            kid.setPlaySite(this);
            return kids.add(kid);
        }
        return false;
    }

    public void removeKid(Kid kid) {
        kids.remove(kid);
    }

    public Set<Kid> getKids() {
        return Collections.unmodifiableSet(kids);
    }

    private void resetDailyVisitorCountIfNewDay() {
        if (!LocalDate.now().equals(lastResetDate)) {
            dailyVisitorCount = 0;
            lastResetDate = LocalDate.now();
        }
    }
}
