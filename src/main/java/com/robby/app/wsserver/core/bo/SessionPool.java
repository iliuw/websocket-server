package com.robby.app.wsserver.core.bo;

import com.robby.app.commons.properties.socketio.SocketIOServerProperties;
import com.robby.app.commons.utils.DateTimeUtil;
import com.robby.app.wsserver.commons.enums.exps.BaseExcept;
import com.robby.app.wsserver.commons.utils.SystemUtils;
import com.robby.app.wsserver.exceptions.WsServerException;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Created @ 2019/11/17
 *
 * @author liuwei
 */
public class SessionPool {

    static final ConcurrentHashMap<String, SessionEntity> sessionMapper = new ConcurrentHashMap<>(1000);
    final SocketIOServerProperties socketProperties;

    public SessionPool(SocketIOServerProperties socketProperties) {
        this.socketProperties = socketProperties;
    }

    private String generateSessionId(String clientId) {
        final String tpl = "%s_session_%s";
        return String.format(tpl, clientId, DateTimeUtil.format(DateTimeUtil.getNow(), "yyyyMMddHHmmSS"));
    }

    public SessionEntity createSession(TheClient client) {
        synchronized (sessionMapper) {
            String clientId = client.getClientId();
            String sessionId = generateSessionId(clientId);
            if(!sessionMapper.containsKey(sessionId)) {
                SessionEntity entity = SessionEntity.builder()
                        .sessionId(sessionId)
                        .owner(client)
                        .build();
                return sessionMapper.putIfAbsent(sessionId, entity);
            } else {
                return sessionMapper.get(sessionId);
            }
        }
    }

    public SessionEntity destroySession(TheClient client) throws WsServerException {
        synchronized (sessionMapper) {
            String sessionId = Optional.ofNullable(SystemUtils.getSocketProperty(client.getIoClient(), socketProperties.getSessionIdPrefix())).orElseThrow(BaseExcept.NULL_WS_SESSION_ID.get());
            return Optional.ofNullable(sessionMapper.remove(sessionId)).orElseGet(null);
        }
    }

    public SessionEntity findSessionById(String sessionId) {
        return sessionMapper.getOrDefault(sessionId, null);
    }

    public List<SessionEntity> findSessionByOwner(TheClient client) {
        synchronized (sessionMapper) {
            List<SessionEntity> list = sessionMapper.values().parallelStream()
                    .filter(m -> m.getSessionId().startsWith(client.getClientId()))
                    .collect(Collectors.toList());
            return list;
        }
    }

    public List<SessionEntity> findSessionByJoiner(TheClient client) {
        synchronized (sessionMapper) {
            String clientId = client.getClientId();
            List<SessionEntity> list = sessionMapper.values().parallelStream()
                    .filter(m -> m.getJoiners().containsKey(clientId))
                    .collect(Collectors.toList());
            return list;
        }
    }

}
