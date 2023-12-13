package com.dcs.common.error.codes;

import com.dcs.common.error.ApiError;
import org.springframework.http.HttpStatus;


public interface IApiErrorCode {

    HttpStatus getHttpStatus();

    ApiError toResponseEntity();

    ApiError toResponseEntity(String detailedMessage);

}
