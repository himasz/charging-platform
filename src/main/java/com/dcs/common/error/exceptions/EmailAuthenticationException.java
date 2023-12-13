package com.dcs.common.error.exceptions;


import com.dcs.common.error.codes.ApiErrorCode;

public class EmailAuthenticationException extends ServiceException {

    public EmailAuthenticationException(final String message) {
        super(message);
    }

    @Override
    public ApiErrorCode getApiErrorCode() {
        return ApiErrorCode.EMAIL_AUTH_FAILED;
    }

}
