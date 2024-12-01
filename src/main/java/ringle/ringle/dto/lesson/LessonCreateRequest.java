package ringle.ringle.dto.lesson;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class LessonCreateRequest {

    private Long tutorId;
    private LocalDateTime startTime;
    private int duration;
}
