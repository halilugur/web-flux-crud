package com.ugurhalil.web.flux.security.model;

import jakarta.validation.constraints.NotNull;

public record PasswordUpdateRequest(@NotNull String username, @NotNull String password, @NotNull String newPassword) {
}
