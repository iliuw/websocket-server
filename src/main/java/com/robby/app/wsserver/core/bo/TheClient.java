package com.robby.app.wsserver.core.bo;

import com.corundumstudio.socketio.SocketIOClient;
import lombok.*;

/**
 * Created @ 2019/11/17
 *
 * @author liuwei
 */
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TheClient {
    String clientId;
    String sessionId;
    SocketIOClient ioClient;
}
