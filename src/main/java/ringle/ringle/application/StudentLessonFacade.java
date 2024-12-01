package ringle.ringle.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ringle.ringle.application.lesson.LessonEnrollmentService;
import ringle.ringle.application.user.StudentService;
import ringle.ringle.domain.lesson.LessonEnrollment;
import ringle.ringle.domain.user.Student;
import ringle.ringle.dto.lesson.LessonEnrollmentRequest;
import ringle.ringle.dto.lesson.LessonEnrollmentResponse;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StudentLessonFacade {
    private final StudentService studentService;
    private final LessonEnrollmentService enrollmentService;

    @Transactional
    public LessonEnrollmentResponse enrollLesson(Long studentId, LessonEnrollmentRequest request) {
        Student student = studentService.getStudent(studentId);
        LessonEnrollment lessonEnrollment = enrollmentService.enrollLesson(student, request);

        return LessonEnrollmentResponse.from(lessonEnrollment);
    }

    public List<LessonEnrollmentResponse> getLessonEnrollmentsByStudent(Long studentId) {
        Student student = studentService.getStudent(studentId);
        List<LessonEnrollment> enrollments = enrollmentService.findByStudent(student);

        return enrollments.stream()
                .map(LessonEnrollmentResponse::from)
                .collect(Collectors.toList());
    }
}
