package com.robby.app.wsserver.commons.enums.exps;

import com.robby.app.wsserver.exceptions.WsServerException;

import java.util.function.Supplier;

/**
 * Created @ 2019/11/17
 *
 * @author liuwei
 */
public enum BaseExcept implements Supplier<WsServerException> {
    DO_FAIL(10000, "Do fail"),
    NULL_WS_SESSION_ID(9999, "Can not find the websocket session id")
    ;
    final WsServerException value;

    BaseExcept(int code, String message) {
        this.value = new WsServerException(code, message);
    }

    @Override
    public WsServerException get() {
        return this.value;
    }
}
