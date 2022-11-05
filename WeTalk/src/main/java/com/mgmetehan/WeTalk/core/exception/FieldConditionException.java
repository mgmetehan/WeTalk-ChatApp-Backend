package com.mgmetehan.WeTalk.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FieldConditionException extends RuntimeException {
    public FieldConditionException(String message) {
        super(message);
    }

    public FieldConditionException(String message, Throwable cause) {
        super(message, cause);
    }

    public FieldConditionException(Throwable cause) {
        super(cause);
    }

    public FieldConditionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
