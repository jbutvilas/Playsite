package lt.jonas.homework.playsite.repository;

import lt.jonas.homework.playsite.model.entity.PlaySite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaySiteRepository extends JpaRepository<PlaySite, Long> {}
