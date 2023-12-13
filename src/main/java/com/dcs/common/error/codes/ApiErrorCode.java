package com.dcs.common.error.codes;

import com.dcs.common.error.ApiError;
import lombok.Getter;
import org.springframework.http.HttpStatus;


public enum ApiErrorCode implements IApiErrorCode {
    MISSING_BODY_FIELD(1, HttpStatus.BAD_REQUEST, "Invalid body field"),
    MISSING_DATA_VALUE(2, HttpStatus.BAD_REQUEST, "Missing data for value"),
    EMAIL_AUTH_FAILED(3, HttpStatus.INTERNAL_SERVER_ERROR, "Authentication failed");

    @Getter
    private final int code;

    @Getter
    private final HttpStatus httpStatus;

    @Getter
    private final String message;

    ApiErrorCode(int code, HttpStatus httpStatus, String message) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.message = message;
    }

    @Override
    public ApiError toResponseEntity() {
        return new ApiError(code, name(), message);
    }

    @Override
    public ApiError toResponseEntity(String detailedMessage) {
        return new ApiError(code, name(), message + ": " + detailedMessage);
    }
}
