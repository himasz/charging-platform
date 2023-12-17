package com.dcs.common.error.exception;


import com.dcs.common.error.DCSErrorCode;

public class DataNotFoundException extends DCSException {

    public DataNotFoundException(final String message) {
        super(message);
    }

    @Override
    public DCSErrorCode getErrorCode() {
        return DCSErrorCode.DATA_NOT_FOUND;
    }

}
