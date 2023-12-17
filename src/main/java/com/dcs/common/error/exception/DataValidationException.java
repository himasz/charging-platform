package com.dcs.common.error.exception;


import com.dcs.common.error.DCSErrorCode;

public class DataValidationException extends DCSException {

    public DataValidationException(final String message) {
        super(message);
    }

    @Override
    public DCSErrorCode getErrorCode() {
        return DCSErrorCode.INVALID_DATA_VALUE;
    }

}
