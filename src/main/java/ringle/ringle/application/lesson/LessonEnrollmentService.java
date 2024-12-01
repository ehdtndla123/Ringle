package ringle.ringle.application.lesson;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import ringle.ringle.common.exception.ErrorCode;
import ringle.ringle.common.exception.LessonException;
import ringle.ringle.dao.LessonEnrollmentRepository;
import ringle.ringle.dao.LessonRepository;
import ringle.ringle.domain.lesson.EnrollmentStatus;
import ringle.ringle.domain.lesson.Lesson;
import ringle.ringle.domain.lesson.LessonEnrollment;
import ringle.ringle.domain.lesson.LessonStatus;
import ringle.ringle.domain.user.Student;
import ringle.ringle.dto.lesson.LessonEnrollmentRequest;
import ringle.ringle.common.event.LessonDeletedEvent;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LessonEnrollmentService {

    private final LessonEnrollmentRepository enrollmentRepository;
    private final LessonRepository lessonRepository;

    @Transactional
    public LessonEnrollment enrollLesson(Student student, LessonEnrollmentRequest request) {
        Lesson lesson = lessonRepository.findById(request.getLessonId())
                .orElseThrow(() -> new LessonException(ErrorCode.LESSON_NOT_FOUND));

        validateEnrollment(lesson, student);

        LessonEnrollment enrollment = LessonEnrollment.builder()
                .student(student)
                .lesson(lesson)
                .status(EnrollmentStatus.ENROLLED)
                .build();

        return enrollmentRepository.save(enrollment);
    }

    public List<LessonEnrollment> findByStudent(Student student) {
        return enrollmentRepository.findByStudent(student);
    }

    private void validateEnrollment(Lesson lesson,Student student) {
        if (lesson.getStatus() != LessonStatus.AVAILABLE) {
            throw new LessonException(ErrorCode.INVALID_LESSON_STATUS);
        }

        boolean alreadyEnrolled = enrollmentRepository.existsByLessonAndStudent(lesson, student);
        if (alreadyEnrolled) {
            throw new LessonException(ErrorCode.ALREADY_ENROLLED);
        }

        LocalDateTime now = LocalDateTime.now();
        if (lesson.getStartTime().isBefore(now)) {
            throw new LessonException(ErrorCode.PAST_ENROLLMENT_TIME);
        }
    }

    @TransactionalEventListener(
            phase = TransactionPhase.AFTER_COMMIT,
            fallbackExecution = true
    )
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handleLessonDeleted(LessonDeletedEvent event) {
        List<LessonEnrollment> enrollments =
                enrollmentRepository.findByLessonAndStatusNot(event.getLesson(), EnrollmentStatus.CANCELLED);
        enrollments.forEach(LessonEnrollment::cancel);
        // 추후 알림 발송
    }
}
