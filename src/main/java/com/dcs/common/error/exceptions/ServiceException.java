package com.dcs.common.error.exceptions;


import com.dcs.common.error.codes.ApiErrorCode;

public abstract class ServiceException extends RuntimeException{

    ServiceException(String message) {
        super(message);
    }

    ServiceException() {
        super();
    }

    public abstract ApiErrorCode getApiErrorCode();

}
