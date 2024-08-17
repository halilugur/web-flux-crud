package com.ugurhalil.web.flux.security.api;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@RequiredArgsConstructor
public class AuthRouter {
    public static final String AUTH_PATH = "/v1/auth";
    public static final String AUTH_PASSWORD_RESET_PATH = "/v1/auth/reset-password";

    private final AuthHandler authHandler;

    @Bean
    public RouterFunction<ServerResponse> authRoutes() {
        return route()
                .POST(AUTH_PATH, authHandler::getToken)
                .POST(AUTH_PASSWORD_RESET_PATH, authHandler::resetPassword)
                .build();
    }
}
