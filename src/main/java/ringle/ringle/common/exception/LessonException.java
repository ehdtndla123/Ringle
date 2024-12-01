package ringle.ringle.common.exception;

public class LessonException extends RuntimeException {
    private final ErrorCode errorCode;

    public LessonException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
