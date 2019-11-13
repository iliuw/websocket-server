package com.robby.app.wsserver.commons.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.google.gson.JsonElement;

import java.util.function.Supplier;

/**
 * Created @ 2019/11/13
 *
 * @author liuwei
 */
public enum ContentType implements Supplier<ContentType> {
    text("text"){
        @Override
        public  <T> T translate(JsonElement source) {
            return null;
        }
    },
    media("media"){
        @Override
        public  <T> T translate(JsonElement source) {
            return null;
        }
    },
    audio("audio"){
        @Override
        public  <T> T translate(JsonElement source) {
            return null;
        }
    },
    vedio("vedio"){
        @Override
        public  <T> T translate(JsonElement source) {
            return null;
        }
    },
    doc("doc"){
        @Override
        public  <T> T translate(JsonElement source) {
            return null;
        }
    },
    monitor("monitor"){
        @Override
        public  <T> T translate(JsonElement source) {
            return null;
        }
    }
    ;
    @EnumValue
    final String label;

    ContentType(String label) {
        this.label = label;
    }

    public abstract <T> T translate(JsonElement source);

    public static ContentType getByLabel(String label) {
        for(ContentType e: ContentType.values()) {
            if(label.equals(e.label)) {
                return e;
            }
        }
        return null;
    }

    @Override
    public ContentType get() {
        return this;
    }
}
