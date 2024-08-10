package com.ugurhalil.web.flux.security.web.fn;

import com.ugurhalil.web.flux.security.model.RoleDto;
import com.ugurhalil.web.flux.security.service.RoleService;
import com.ugurhalil.web.flux.security.service.ValidatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class RoleHandler {
    private final RoleService roleService;
    private final ValidatorService validatorService;

    public Mono<ServerResponse> getAll(ServerRequest request) {
        return ServerResponse.ok()
                .body(roleService.getAll(), RoleDto.class);
    }

    public Mono<ServerResponse> getById(ServerRequest request) {
        return roleService.getById(request.pathVariable("id"))
                .flatMap(roleDto -> ServerResponse.ok()
                        .bodyValue(roleDto))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> save(ServerRequest request) {
        return request.bodyToMono(RoleDto.class)
                .doOnNext(validatorService::validate)
                .flatMap(roleService::save)
                .flatMap(roleDto -> ServerResponse.created(URI.create(RoleRouter.ROLE_PATH + "/" + roleDto.getId()))
                        .bodyValue(roleDto));
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        return request.bodyToMono(RoleDto.class)
                .doOnNext(validatorService::validate)
                .flatMap(roleDto -> roleService.update(request.pathVariable("id"), roleDto))
                .flatMap(roleDto -> ServerResponse.ok()
                        .bodyValue(roleDto));
    }
}
