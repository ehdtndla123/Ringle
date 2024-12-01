package ringle.ringle.dto.lesson;

import lombok.Builder;
import lombok.Getter;
import ringle.ringle.domain.lesson.EnrollmentStatus;
import ringle.ringle.domain.lesson.Lesson;
import ringle.ringle.domain.lesson.LessonEnrollment;

import java.time.LocalDateTime;

@Getter
@Builder
public class LessonEnrollmentResponse {

    private Long id;
    private Long studentId;
    private Long lessonId;
    private LocalDateTime startTime;
    private int duration;
    private String tutorName;
    private EnrollmentStatus status;

    public static LessonEnrollmentResponse from(LessonEnrollment enrollment) {
        Lesson lesson = enrollment.getLesson();
        return LessonEnrollmentResponse.builder()
                .id(enrollment.getId())
                .studentId(enrollment.getStudent().getId())
                .lessonId(lesson.getId())
                .startTime(lesson.getStartTime())
                .duration(lesson.getDuration())
                .tutorName(lesson.getTutor().getName())
                .status(enrollment.getStatus())
                .build();
    }
}