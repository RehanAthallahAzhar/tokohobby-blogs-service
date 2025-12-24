package com.tokohobby.blogs.security;

import com.tokohobby.blogs.grpc.auth.AuthServiceGrpc;
import com.tokohobby.blogs.grpc.auth.ValidateTokenRequest;
import com.tokohobby.blogs.grpc.auth.ValidateTokenResponse;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class AuthGrpcClient {

    @GrpcClient("accounts-service")
    private AuthServiceGrpc.AuthServiceBlockingStub authServiceBlockingStub;

    public ValidateTokenResponse validateToken(String token) {
        ValidateTokenRequest request = ValidateTokenRequest.newBuilder()
                .setToken(token)
                .build();
        try {
            return authServiceBlockingStub.validateToken(request);
        } catch (Exception e) {
            return ValidateTokenResponse.newBuilder()
                    .setIsValid(false)
                    .setErrorMessage(e.getMessage())
                    .build();
        }
    }
}
