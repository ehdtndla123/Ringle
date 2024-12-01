package ringle.ringle.dto.lesson;

import lombok.Builder;
import lombok.Getter;
import ringle.ringle.domain.lesson.Lesson;
import ringle.ringle.domain.lesson.LessonStatus;

import java.time.LocalDateTime;

@Getter
@Builder
public class LessonCreateResponse {

    private Long id;
    private LocalDateTime startTime;
    private int duration;
    private LessonStatus status;

    public static LessonCreateResponse from(Lesson lesson) {
        return LessonCreateResponse.builder()
                .id(lesson.getId())
                .startTime(lesson.getStartTime())
                .duration(lesson.getDuration())
                .status(lesson.getStatus())
                .build();
    }
}
