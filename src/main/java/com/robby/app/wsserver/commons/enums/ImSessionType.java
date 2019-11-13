package com.robby.app.wsserver.commons.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

import java.util.function.Supplier;

/**
 * Created @ 2019/11/13
 *
 * @author liuwei
 */
public enum ImSessionType implements Supplier<ImSessionType> {
    single("single"),
    multi("multi")
    ;
    @EnumValue
    final String type;

    ImSessionType(String type) {
        this.type = type;
    }

    @Override
    public ImSessionType get() {
        return this;
    }
}
