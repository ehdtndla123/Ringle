package ringle.ringle.domain.lesson;

import lombok.Getter;
import ringle.ringle.common.exception.ErrorCode;
import ringle.ringle.common.exception.LessonException;

import java.util.Arrays;

@Getter
public enum LessonDuration {
    MINUTES_30(30),
    MINUTES_60(60);

    private final int minutes;

    LessonDuration(int minutes) {
        this.minutes = minutes;
    }

    public static boolean isValid(int minutes) {
        return Arrays.stream(values())
                .anyMatch(duration -> duration.minutes == minutes);
    }
}
