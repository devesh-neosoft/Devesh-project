package com.devesh.exception;

public enum ErrorCode {
    SUCCESS(200),
    INVALIDFORMAT(101),
    EMPTYFOUND(102),
    ALREADYFOUND(103),
    COMMON(111);

    int code;

    ErrorCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
