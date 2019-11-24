package com.robby.app.wsserver.core;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.robby.app.wsserver.pojo.dto.message.WebSocketMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Created @ 2019/11/17
 *
 * @author liuwei
 */
@Slf4j
@Component
public class WebSocketHandle {

    @OnConnect
    public void connect(SocketIOClient client) {
        // TODO
    }

    @OnDisconnect
    public void disconnect(SocketIOClient client) {
        // TODO
    }

    /**
     * 动作
     * @param ioClient
     * @param request
     * @param message
     */
    @OnEvent(value = "C_ACTION")
    public void doAction(SocketIOClient ioClient, AckRequest request, WebSocketMessage message) {
        // TODO
    }

    /**
     * 通知消息
     * @param ioClient
     * @param request
     * @param message
     */
    @OnEvent(value = "C_NOTICE")
    public void transmitNotice(SocketIOClient ioClient, AckRequest request, WebSocketMessage message) {
        // TODO
    }

    /**
     * IM消息
     * @param ioClient
     * @param request
     * @param message
     */
    @OnEvent(value = "C_IM")
    public void transmitIm(SocketIOClient ioClient, AckRequest request, WebSocketMessage message) {
        // TODO
    }

}
