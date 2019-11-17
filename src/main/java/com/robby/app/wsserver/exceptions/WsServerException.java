package com.robby.app.wsserver.exceptions;

import java.util.function.Supplier;

/**
 * Created @ 2019/11/17
 *
 * @author liuwei
 */
public class WsServerException extends Throwable implements Supplier<WsServerException> {
    int code;
    String message;

    public WsServerException(int code) {
        this.code = code;
    }

    public WsServerException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public WsServerException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = String.format("%s\n\t%s", message, cause);
    }

    public WsServerException(int code, Throwable cause) {
        super(cause);
        this.code = code;
        this.message = cause.getMessage();
    }

    public WsServerException(int code, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
        this.message = message;
    }

    @Override
    public WsServerException get() {
        return this;
    }
}
