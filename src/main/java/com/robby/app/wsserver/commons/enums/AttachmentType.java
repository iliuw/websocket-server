package com.robby.app.wsserver.commons.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

import java.util.function.Supplier;

/**
 * Created @ 2019/11/13
 *
 * @author liuwei
 */
public enum AttachmentType implements Supplier<AttachmentType> {
    file("file"),
    image("image"),
    audio("audio"),
    vedio("vedio")
    ;
    @EnumValue
    final String type;

    AttachmentType(String type) {
        this.type = type;
    }

    @Override
    public AttachmentType get() {
        return this;
    }
}
