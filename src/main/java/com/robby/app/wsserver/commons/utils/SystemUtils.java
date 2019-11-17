package com.robby.app.wsserver.commons.utils;

import com.corundumstudio.socketio.SocketIOClient;

/**
 * Created @ 2019/11/17
 *
 * @author liuwei
 */
public class SystemUtils {
    public static String getSocketProperty(SocketIOClient client, String key) {
        try{
            String fullKey = String.format("X-server-%s", key);
            if(client.getHandshakeData().getHttpHeaders().contains(fullKey)) {
                return client.getHandshakeData().getHttpHeaders().get(fullKey);
            } else {
                return client.getHandshakeData().getSingleUrlParam(key);
            }
        } catch(NullPointerException e) {
            return null;
        }
    }
}
