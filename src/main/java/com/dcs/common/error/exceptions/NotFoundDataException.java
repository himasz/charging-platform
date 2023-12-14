package com.dcs.common.error.exceptions;


import com.dcs.common.error.codes.ApiErrorCode;

public class NotFoundDataException extends ServiceException {

    public NotFoundDataException(final String message) {
        super(message);
    }

    @Override
    public ApiErrorCode getApiErrorCode() {
        return ApiErrorCode.NOT_FOUND_DATA;
    }

}
