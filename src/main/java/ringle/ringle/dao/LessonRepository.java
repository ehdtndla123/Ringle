package ringle.ringle.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ringle.ringle.domain.lesson.Lesson;

import java.time.LocalDateTime;
import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
    @Query("SELECT l FROM Lesson l WHERE l.status = 'AVAILABLE' " +
            "AND l.startTime BETWEEN :startTime AND :endTime " +
            "AND l.duration = :duration")
    List<Lesson> findAvailableLessons(LocalDateTime startTime, LocalDateTime endTime, int duration);
}
