package com.ugurhalil.web.flux.security.web.fn;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@RequiredArgsConstructor
public class UserRouter {
    public static final String BASE_PATH_V1 = "/v1/users";
    public static final String USER_PATH_BY_ID = BASE_PATH_V1 + "/{id}";

    private final UserHandler userHandler;

    @Bean
    public RouterFunction<ServerResponse> userRoutes() {
        return route()
                .GET(BASE_PATH_V1, userHandler::getAll)
                .GET(USER_PATH_BY_ID, userHandler::getById)
                .POST(BASE_PATH_V1, userHandler::createUser)
                .PUT(USER_PATH_BY_ID, userHandler::updateUser)
                .build();
    }
}

