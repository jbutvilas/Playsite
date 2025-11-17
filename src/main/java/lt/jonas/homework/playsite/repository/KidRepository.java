package lt.jonas.homework.playsite.repository;

import lt.jonas.homework.playsite.model.entity.Kid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KidRepository extends JpaRepository<Kid, Long> {}
