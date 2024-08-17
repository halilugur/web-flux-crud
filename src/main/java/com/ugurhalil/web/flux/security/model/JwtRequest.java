package com.ugurhalil.web.flux.security.model;

import jakarta.validation.constraints.NotNull;

public record JwtRequest(@NotNull String username, @NotNull String password) {
}
