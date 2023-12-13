package com.dcs.common.error.exceptions;


import com.dcs.common.error.codes.ApiErrorCode;

public class MissingDataException extends ServiceException {

    public MissingDataException(final String message) {
        super(message);
    }

    @Override
    public ApiErrorCode getApiErrorCode() {
        return ApiErrorCode.MISSING_DATA_VALUE;
    }

}
