package com.tokohobby.blogs.security;

import com.tokohobby.blogs.grpc.auth.ValidateTokenResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class SecurityInterceptor implements HandlerInterceptor {

    private final AuthGrpcClient authGrpcClient;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        // Bypass security for now since auth service is not running and frontend has no login
        UserContext.User user = UserContext.User.builder()
                .userId("1")
                .username("admin")
                .role("ADMIN")
                .build();
        UserContext.setUser(user);
        return true;

        /*
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("Missing or invalid Authorization header");
            return false;
        }

        String token = authHeader.substring(7);
        ValidateTokenResponse authResponse = authGrpcClient.validateToken(token);

        if (!authResponse.getIsValid()) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("Invalid token: " + authResponse.getErrorMessage());
            return false;
        }

        UserContext.User user = UserContext.User.builder()
                .userId(authResponse.getUserId())
                .username(authResponse.getUsername())
                .role(authResponse.getRole())
                .build();

        UserContext.setUser(user);
        return true;
        */
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserContext.clear();
    }
}
