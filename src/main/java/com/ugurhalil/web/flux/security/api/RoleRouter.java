package com.ugurhalil.web.flux.security.api;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@RequiredArgsConstructor
public class RoleRouter {
    public static final String ROLE_PATH = "/v1/roles";
    public static final String ROLE_ID_PATH = "/v1/roles/{id}";

    private final RoleHandler roleHandler;

    @Bean
    public RouterFunction<ServerResponse> roleRoutes() {
        return route()
                .GET(ROLE_PATH, roleHandler::getAll)
                .GET(ROLE_ID_PATH, roleHandler::getById)
                .POST(ROLE_PATH, roleHandler::save)
                .PUT(ROLE_ID_PATH, roleHandler::update)
                .build();
    }
}
