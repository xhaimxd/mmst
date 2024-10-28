package com.roger.mmst.constants.exception;

/**
 @author Roger Liu
 @date 2024/03/06
 */
public class WsNotifyException extends RuntimeException {
    public WsNotifyException() {
    }

    public WsNotifyException(String message) {
        super(message);
    }

    public WsNotifyException(String message, Throwable cause) {
        super(message, cause);
    }

    public WsNotifyException(Throwable cause) {
        super(cause);
    }

    public WsNotifyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
