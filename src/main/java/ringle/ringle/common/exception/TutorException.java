package ringle.ringle.common.exception;

public class TutorException extends RuntimeException {
    private final ErrorCode errorCode;

    public TutorException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
