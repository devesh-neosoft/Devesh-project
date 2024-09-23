package com.devesh.exception;

public class CodeException extends RuntimeException {
    private final ErrorCode code;

    public CodeException(ErrorCode code) {
        super();
        this.code = code;
    }

    public CodeException(String message, Throwable cause, ErrorCode code) {
        super(message, cause);
        this.code = code;
    }

    public CodeException(String message, ErrorCode code) {
        super(message);
        this.code = code;
    }

    public CodeException(Throwable cause, ErrorCode code) {
        super(cause);
        this.code = code;
    }

    public ErrorCode getCode() {
        return this.code;
    }
}