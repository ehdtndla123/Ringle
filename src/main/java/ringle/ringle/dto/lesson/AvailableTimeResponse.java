package ringle.ringle.dto.lesson;

import lombok.Builder;
import lombok.Getter;
import ringle.ringle.domain.lesson.Lesson;

import java.time.LocalDateTime;

@Getter
@Builder
public class AvailableTimeResponse {

    private Long lessonId;
    private LocalDateTime startTime;
    private int duration;

    public static AvailableTimeResponse from(Lesson lesson) {
        return AvailableTimeResponse.builder()
                .lessonId(lesson.getId())
                .startTime(lesson.getStartTime())
                .duration(lesson.getDuration())
                .build();
    }
}
