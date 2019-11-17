package com.robby.app.wsserver.configures;

import com.corundumstudio.socketio.AuthorizationListener;
import com.corundumstudio.socketio.HandshakeData;
import com.corundumstudio.socketio.SocketConfig;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;
import com.robby.app.commons.properties.socketio.SocketIOServerProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created @ 2019/11/17
 *
 * @author liuwei
 */
@Slf4j
@Configuration
public class WebSocketConfiguration {

    @Value("${server.ws-port}")
    int wsPort;

    @Bean
    @ConfigurationProperties(prefix = "sysconf.socketio")
    public SocketIOServerProperties socketProperties() {
        return new SocketIOServerProperties();
    }

    @Bean
    @Qualifier("socketProperties")
    public SocketIOServer socketIOServer(@Autowired SocketIOServerProperties socketProperties) {
        com.corundumstudio.socketio.Configuration configure = new com.corundumstudio.socketio.Configuration();
        // 配置参数
        configure.setPort(wsPort);
//        configure.setHostname("localhost");
        configure.setUpgradeTimeout(socketProperties.getUpgradeTimeout());
        configure.setPingInterval(socketProperties.getPingInterval());
        configure.setPingTimeout(socketProperties.getPingTimeout());
        configure.setBossThreads(socketProperties.getBossCount());
        configure.setWorkerThreads(socketProperties.getWorkCount());
        configure.setAllowCustomRequests(socketProperties.isAllowCustomRequests());

        SocketConfig socketConfig = new SocketConfig();
        socketConfig.setTcpNoDelay(true);
        socketConfig.setSoLinger(0);
        configure.setSocketConfig(socketConfig);
        // 授权验证
        configure.setAuthorizationListener(new AuthorizationListener() {
            @Override
            public boolean isAuthorized(HandshakeData data) {
                // TODO
                return true;
            }
        });
        return new SocketIOServer(configure);
    }

    @Bean
    @Qualifier("socketIOServer")
    public SpringAnnotationScanner springAnnotationScanner(@Autowired SocketIOServer socketIOServer) {
        return new SpringAnnotationScanner(socketIOServer);
    }

}
