package com.ugurhalil.web.flux.security.api;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@RequiredArgsConstructor
public class UserRouter {
    public static final String USER_PATH = "/v1/users";
    public static final String USER_PATH_BY_ID = USER_PATH + "/{id}";

    private final UserHandler userHandler;

    @Bean
    public RouterFunction<ServerResponse> userRoutes() {
        return route()
                .GET(USER_PATH, userHandler::getAll)
                .GET(USER_PATH_BY_ID, userHandler::getById)
                .POST(USER_PATH, userHandler::createUser)
                .PUT(USER_PATH_BY_ID, userHandler::updateUser)
                .build();
    }
}

