package com.robby.app.wsserver.core;

import com.corundumstudio.socketio.SocketIOServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Created @ 2019/11/17
 *
 * @author liuwei
 */
@Slf4j
@Component
public class WebSocketRunner implements CommandLineRunner {
    final SocketIOServer socketIOServer;
    @Value("${server.ws-port}")
    int port;

    @Autowired
    public WebSocketRunner(SocketIOServer socketIOServer) {
        this.socketIOServer = socketIOServer;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("== [Command] ===+> Starting websocket server ...");
        TimeUnit.SECONDS.sleep(2);
        socketIOServer.start();
        log.info("== [Command] ===+> Websocket server is started. Service port: {}", port);
    }
}
