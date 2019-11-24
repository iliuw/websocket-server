package com.robby.app.wsserver.pojo.dto.message;

import java.util.function.Supplier;

/**
 * Created @ 2019/11/17
 *
 * @author liuwei
 */
public interface WebSocketMessage<T extends Object> extends Supplier<T> {
}
