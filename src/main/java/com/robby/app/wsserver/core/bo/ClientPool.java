package com.robby.app.wsserver.core.bo;

import com.corundumstudio.socketio.SocketIOClient;
import com.robby.app.commons.properties.socketio.SocketIOServerProperties;
import com.robby.app.wsserver.commons.utils.SystemUtils;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Created @ 2019/11/17
 *
 * @author liuwei
 */
public class ClientPool {
    static final ConcurrentHashMap<String, TheClient> clientMapper = new ConcurrentHashMap<>(1000);
    final SocketIOServerProperties socketProperties;

    public ClientPool(SocketIOServerProperties socketProperties) {
        this.socketProperties = socketProperties;
    }

    public List<String> allClientIds() {
        synchronized (clientMapper) {
            if(!clientMapper.isEmpty()) {
                return clientMapper.keySet().parallelStream().collect(Collectors.toList());
            }
            return null;
        }
    }

    public List<TheClient> allClients() {
        synchronized (clientMapper) {
            if(!clientMapper.isEmpty()) {
                return clientMapper.values().parallelStream().collect(Collectors.toList());
            }
            return null;
        }
    }

    public TheClient newConnect(SocketIOClient ioClient) {
        synchronized (clientMapper) {
            String id = SystemUtils.getSocketProperty(ioClient, socketProperties.getIdleIdPrefix());
            if(clientMapper.containsKey(id)) {
                return clientMapper.get(id);
            }
            TheClient newClient = TheClient.builder()
                    .clientId(id)
                    .ioClient(ioClient)
                    .build();
            return clientMapper.putIfAbsent(id, newClient);
        }
    }

    public TheClient disconnect(SocketIOClient ioClient) {
        synchronized (clientMapper) {
            String id = SystemUtils.getSocketProperty(ioClient, socketProperties.getIdleIdPrefix());
            if(clientMapper.containsKey(id)) {
                return clientMapper.remove(id);
            }
            return null;
        }
    }

    public TheClient findClient(String id) {
        return clientMapper.getOrDefault(id, null);
    }
}
