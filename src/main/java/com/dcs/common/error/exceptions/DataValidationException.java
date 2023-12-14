package com.dcs.common.error.exceptions;


import com.dcs.common.error.codes.ApiErrorCode;

public class DataValidationException extends ServiceException {

    public DataValidationException(final String message) {
        super(message);
    }

    @Override
    public ApiErrorCode getApiErrorCode() {
        return ApiErrorCode.INVALID_DATA_VALUE;
    }

}
