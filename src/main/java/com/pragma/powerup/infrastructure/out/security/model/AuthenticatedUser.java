package com.pragma.powerup.infrastructure.out.security.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthenticatedUser {
    private final Long id;
    private final String email;
    private final String role;
}
