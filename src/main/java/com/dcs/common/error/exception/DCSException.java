package com.dcs.common.error.exception;


import com.dcs.common.error.DCSErrorCode;

public abstract class DCSException extends RuntimeException{

    DCSException(String message) {
        super(message);
    }

    DCSException() {
        super();
    }

    public abstract DCSErrorCode getErrorCode();

}
