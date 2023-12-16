package com.dcs.common.error.codes;

import com.dcs.common.error.ApiError;
import lombok.Getter;
import org.springframework.http.HttpStatus;


public enum ApiErrorCode implements IApiErrorCode {
    MISSING_BODY_FIELD(1, HttpStatus.BAD_REQUEST, "Invalid body field"),
    INVALID_DATA_VALUE(2, HttpStatus.BAD_REQUEST, "Invalid data for value"),
    DATA_NOT_FOUND(3, HttpStatus.BAD_REQUEST, "No data found for value");

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
