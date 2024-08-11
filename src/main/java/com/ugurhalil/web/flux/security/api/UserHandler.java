package com.ugurhalil.web.flux.security.api;

import com.ugurhalil.web.flux.security.model.UserCreateRequest;
import com.ugurhalil.web.flux.security.model.UserDto;
import com.ugurhalil.web.flux.security.model.UserUpdateRequest;
import com.ugurhalil.web.flux.security.service.UserService;
import com.ugurhalil.web.flux.security.service.ValidatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class UserHandler {
    private final UserService userService;
    private final ValidatorService validatorService;

    public Mono<ServerResponse> getAll(ServerRequest request) {
        Flux<UserDto> userDtoFlux = userService.getAll();
        return ServerResponse.ok().body(userDtoFlux, UserDto.class);
    }

    public Mono<ServerResponse> createUser(ServerRequest request) {
        return request.bodyToMono(UserCreateRequest.class)
                .doOnNext(validatorService::validate)
                .flatMap(userService::save)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST)))
                .flatMap(userDto -> ServerResponse.created(URI.create(RoleRouter.ROLE_PATH + "/" + userDto.getId()))
                        .build());
    }

    public Mono<ServerResponse> getById(ServerRequest request) {
        String id = request.pathVariable("id");
        return userService.getById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .flatMap(userDto -> ServerResponse.ok().bodyValue(userDto));
    }

    public Mono<ServerResponse> updateUser(ServerRequest request) {
        String id = request.pathVariable("id");
        return request.bodyToMono(UserUpdateRequest.class)
                .doOnNext(validatorService::validate)
                .flatMap(updateRequest -> userService.update(id, updateRequest))
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .flatMap(userDto -> ServerResponse.ok().bodyValue(userDto));
    }
}
