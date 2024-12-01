package ringle.ringle.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ringle.ringle.domain.user.Tutor;

import java.time.LocalDateTime;
import java.util.List;

public interface TutorRepository extends JpaRepository<Tutor, Long> {
    @Query("SELECT DISTINCT l.tutor FROM Lesson l WHERE l.status = 'AVAILABLE' " +
            "AND l.startTime = :startTime AND l.duration = :duration")
    List<Tutor> findAvailableTutors(LocalDateTime startTime, int duration);
}
