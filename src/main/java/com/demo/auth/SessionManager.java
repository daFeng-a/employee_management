package com.demo.auth;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Component
public class SessionManager {

    private final Map<String, SessionInfo> sessionCache = new ConcurrentHashMap<>();
    private final long sessionTimeoutMs = TimeUnit.HOURS.toMillis(2); // 会话超时时间：2小时

    public String createSession(String username) {
        String sessionId = UUID.randomUUID().toString();
        SessionInfo sessionInfo = new SessionInfo(username, System.currentTimeMillis() + sessionTimeoutMs);
        sessionCache.put(sessionId, sessionInfo);
        return sessionId;
    }

    public boolean validateSession(String sessionId) {
        if (!sessionCache.containsKey(sessionId)) {
            return false;
        }

        SessionInfo sessionInfo = sessionCache.get(sessionId);
        if (System.currentTimeMillis() > sessionInfo.getExpirationTime()) {
            sessionCache.remove(sessionId); // 移除过期会话
            return false;
        }

        // 刷新会话过期时间
        sessionInfo.setExpirationTime(System.currentTimeMillis() + sessionTimeoutMs);
        return true;
    }

    public String getUsername(String sessionId) {
        if (validateSession(sessionId)) {
            return sessionCache.get(sessionId).getUsername();
        }
        return null;
    }

    public void removeSession(String sessionId) {
        sessionCache.remove(sessionId);
    }

    // 会话信息内部类
    @Getter
    @AllArgsConstructor
    public static class SessionInfo {
        private final String username;

        @Setter
        private long expirationTime;

    }
}
