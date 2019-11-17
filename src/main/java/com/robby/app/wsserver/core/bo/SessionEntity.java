package com.robby.app.wsserver.core.bo;

import lombok.*;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created @ 2019/11/17
 *
 * @author liuwei
 */
@Data
@ToString
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class SessionEntity {
    String sessionId;
    @Builder.Default
    Map<String, TheClient> joiners = new LinkedHashMap<>();
    TheClient owner;
}
