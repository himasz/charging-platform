package com.dcs.common.error.exceptions;


import com.dcs.common.error.codes.ApiErrorCode;

public class DataNotFoundException extends ServiceException {

    public DataNotFoundException(final String message) {
        super(message);
    }

    @Override
    public ApiErrorCode getApiErrorCode() {
        return ApiErrorCode.NOT_FOUND_DATA;
    }

}
