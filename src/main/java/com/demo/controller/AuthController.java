package com.demo.controller;


import com.demo.auth.SessionManager;
import com.demo.common.AjaxResult;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private SessionManager sessionManager;

    @PostMapping("/login")
    public AjaxResult authenticateUser(@RequestBody Map<String, String> loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.get("username"),
                        loginRequest.get("password")
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String username = authentication.getName();

        // 创建会话并获取会话ID
        String sessionId = sessionManager.createSession(username);

        Map<String, Object> response = new HashMap<>();
        response.put("token", sessionId);
        response.put("username", username);

        return AjaxResult.success(response);
    }

    @PostMapping("/logout")
    public AjaxResult logoutUser(@RequestHeader("Authorization") String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String sessionId = authHeader.substring(7);
            sessionManager.removeSession(sessionId);
        }
        return new AjaxResult(200, "退出登录成功", null);
    }
}
