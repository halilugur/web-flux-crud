package com.ugurhalil.web.flux.security.security;

import com.ugurhalil.web.flux.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
class JwtServerAuthenticationConverter implements ServerAuthenticationConverter {
    private static final String BEARER = "Bearer ";
    private final AuthService authService;

    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        return Mono.justOrEmpty(exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION))
                .filter(header -> header.startsWith(BEARER))
                .map(header -> header.substring(BEARER.length()))
                .map(token -> new JwtToken(token, createUserDetails(token)));
    }

    private UserDetails createUserDetails(String token) {
        String username = authService.extractUsername(token);
        return User.builder()
                .username(username)
                .authorities(createAuthorities(token))
                .password("") // bypass password null check
                .build();
    }

    private List<SimpleGrantedAuthority> createAuthorities(String token) {
        return authService.extractRoles(token).stream()
                .map(role -> "ROLE_" + role)
                .map(SimpleGrantedAuthority::new)
                .toList();
    }
}