package ringle.ringle.common.event;

import lombok.Getter;
import ringle.ringle.domain.lesson.Lesson;

import java.time.LocalDateTime;

@Getter
public class LessonDeletedEvent {
    private final Lesson lesson;
    private final LocalDateTime deletedAt;

    public LessonDeletedEvent(Lesson lesson) {
        this.lesson = lesson;
        this.deletedAt = LocalDateTime.now();
    }
}
