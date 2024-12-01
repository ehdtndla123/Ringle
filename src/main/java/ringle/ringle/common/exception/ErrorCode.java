package ringle.ringle.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    LESSON_NOT_FOUND(HttpStatus.NOT_FOUND, "수업을 찾을 수 없습니다."),
    UNAUTHORIZED_TUTOR(HttpStatus.FORBIDDEN, "해당 수업의 튜터가 아닙니다."),
    INVALID_LESSON_STATUS(HttpStatus.BAD_REQUEST, "삭제할 수 없는 상태의 수업입니다."),
    INVALID_DURATION(HttpStatus.BAD_REQUEST, "수업 시간이 올바르지 않습니다."),
    INVALID_START_TIME(HttpStatus.BAD_REQUEST, "수업 시작 시간이 올바르지 않습니다."),
    PASS_START_TIME(HttpStatus.BAD_REQUEST, "지난 시간의 수업을 생성할 수 없습니다."),

    STUDENT_NOT_FOUND(HttpStatus.NOT_FOUND, "학생을 찾을 수 없습니다."),
    TUTOR_NOT_FOUND(HttpStatus.NOT_FOUND, "튜터를 찾을 수 없습니다."),

    ALREADY_ENROLLED(HttpStatus.CONFLICT, "이미 신청한 수업입니다."),
    PAST_ENROLLMENT_TIME(HttpStatus.BAD_REQUEST, "지난 시간의 수업은 신청할 수 없습니다.");

    private final HttpStatus status;
    private final String message;
}