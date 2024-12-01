package ringle.ringle.common.exception;

public class StudentException extends RuntimeException{
    private final ErrorCode errorCode;

    public StudentException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
