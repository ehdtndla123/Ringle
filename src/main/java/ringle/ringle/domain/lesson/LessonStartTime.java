package ringle.ringle.domain.lesson;

import lombok.Getter;
import java.time.LocalDateTime;
import java.util.Arrays;

@Getter
public enum LessonStartTime {
    ON_THE_HOUR(0),
    HALF_PAST(30);

    private final int minutes;

    LessonStartTime(int minutes) {
        this.minutes = minutes;
    }

    public static boolean isValid(LocalDateTime time) {
        return Arrays.stream(values())
                .anyMatch(startTime -> startTime.minutes == time.getMinute());
    }
}