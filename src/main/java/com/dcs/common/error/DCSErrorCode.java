package com.dcs.common.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public enum DCSErrorCode {
        NOT_FOUND_BODY_FIELD(1, HttpStatus.BAD_REQUEST, "Not found or invalid body field"),
        INVALID_DATA_VALUE(2, HttpStatus.BAD_REQUEST, "Invalid value for "),
        DATA_NOT_FOUND(3, HttpStatus.BAD_REQUEST, "No data found for value");

        @Getter
        private final int code;

        @Getter
        private final HttpStatus status;

        @Getter
        private final String message;

        DCSErrorCode(int code, HttpStatus status, String message) {
            this.code = code;
            this.status = status;
            this.message = message;
        }

        public DCSError response(String message) {
            return new DCSError(code, name(), this.message + ": " + message);
        }
    }
