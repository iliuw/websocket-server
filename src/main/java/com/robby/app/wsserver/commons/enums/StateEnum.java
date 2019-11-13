package com.robby.app.wsserver.commons.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

import java.util.function.Supplier;

/**
 * Created @ 2019/11/13
 *
 * @author liuwei
 */
public enum StateEnum implements Supplier<StateEnum> {
    alive("alive"),
    dead("dead"),
    pushing("pushing"),
    pushed("pushed"),
    timeout("timeout"),
    repeal("repeal"),
    unpush("unpush")
    ;
    @EnumValue
    final String label;

    StateEnum(String label) {
        this.label = label;
    }

    @Override
    public StateEnum get() {
        return this;
    }
}
