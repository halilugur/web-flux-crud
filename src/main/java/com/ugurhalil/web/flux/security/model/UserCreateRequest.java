package com.ugurhalil.web.flux.security.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserCreateRequest(@NotNull @Size(min = 3, max = 20) String username,
                                String email,
                                @NotNull String password) {
}
