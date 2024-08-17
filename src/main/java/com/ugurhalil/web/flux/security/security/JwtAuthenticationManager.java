package com.ugurhalil.web.flux.security.security;

import com.ugurhalil.web.flux.security.exception.ProjectException;
import com.ugurhalil.web.flux.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
class JwtAuthenticationManager implements ReactiveAuthenticationManager {

    private final AuthService authService;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return Mono.just(authentication)
                .cast(JwtToken.class)
                .filter(jwtToken -> authService.isTokenValid(jwtToken.getToken()))
                .map(jwtToken -> jwtToken.withAuthenticated(true))
                .switchIfEmpty(Mono.error(new ProjectException("Invalid token.")));
    }
}