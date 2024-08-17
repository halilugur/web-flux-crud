package com.ugurhalil.web.flux.security.api;

import com.ugurhalil.web.flux.security.model.JwtRequest;
import com.ugurhalil.web.flux.security.model.JwtResponse;
import com.ugurhalil.web.flux.security.model.PasswordUpdateRequest;
import com.ugurhalil.web.flux.security.service.AuthService;
import com.ugurhalil.web.flux.security.service.UserService;
import com.ugurhalil.web.flux.security.service.ValidatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AuthHandler {

    private final ReactiveUserDetailsService userDetailsService;
    private final ValidatorService validatorService;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;
    private final UserService userService;

    public Mono<ServerResponse> getToken(ServerRequest request) {
        return request.bodyToMono(JwtRequest.class)
                .doOnNext(validatorService::validate)
                .flatMap(token -> userDetailsService.findByUsername(token.username())
                        .filter(u -> passwordEncoder.matches(token.password(), u.getPassword()))
                        .map(authService::generateToken)
                        .map(JwtResponse::new)
                        .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED))))
                .flatMap(token -> ServerResponse.ok().bodyValue(token));

    }

    public Mono<ServerResponse> resetPassword(ServerRequest request) {
        return request.bodyToMono(PasswordUpdateRequest.class)
                .doOnNext(validatorService::validate)
                .flatMap(passwordUpdateRequest -> userDetailsService.findByUsername(passwordUpdateRequest.username())
                        .filter(u -> passwordEncoder.matches(passwordUpdateRequest.password(), u.getPassword()))
                        .flatMap(user -> userService.updatePassword(user, passwordUpdateRequest.newPassword()))
                        .map(authService::generateToken)
                        .map(JwtResponse::new)
                        .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED))))
                .flatMap(token -> ServerResponse.ok().bodyValue(token));
    }
}
