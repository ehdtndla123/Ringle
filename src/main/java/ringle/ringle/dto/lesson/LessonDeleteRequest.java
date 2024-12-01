package ringle.ringle.dto.lesson;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LessonDeleteRequest {

    private Long tutorId;
    private Long lessonId;
    private String reason;
}
