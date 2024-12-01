package ringle.ringle.dao;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ringle.ringle.domain.lesson.EnrollmentStatus;
import ringle.ringle.domain.lesson.Lesson;
import ringle.ringle.domain.lesson.LessonEnrollment;
import ringle.ringle.domain.user.Student;

import java.util.List;

public interface LessonEnrollmentRepository extends JpaRepository<LessonEnrollment,Long> {

    List<LessonEnrollment> findByStudentAndStatusNot(Student student, EnrollmentStatus status);

    @EntityGraph(attributePaths = {"lesson", "lesson.tutor"})
    List<LessonEnrollment> findByStudent(Student student);

    boolean existsByLessonAndStudent(Lesson lesson, Student student);
    List<LessonEnrollment> findByLessonAndStatusNot(Lesson lesson, EnrollmentStatus status);
}
