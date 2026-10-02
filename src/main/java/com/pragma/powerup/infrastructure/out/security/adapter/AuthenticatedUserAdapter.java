package com.pragma.powerup.infrastructure.out.security.adapter;

import com.pragma.powerup.domain.spi.IAuthenticatedUserPort;
import com.pragma.powerup.infrastructure.out.security.model.AuthenticatedUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticatedUserAdapter implements IAuthenticatedUserPort {

    @Override
    public Long obtenerIdUsuarioAutenticado() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof AuthenticatedUser) {
            return ((AuthenticatedUser) auth.getPrincipal()).getId();
        }
        return null;
    }

    @Override
    public String obtenerRolUsuarioAutenticado() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof AuthenticatedUser) {
            return ((AuthenticatedUser) auth.getPrincipal()).getRole();
        }
        return null;
    }

    @Override
    public String obtenerCorreoUsuarioAutenticado() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof AuthenticatedUser) {
            return ((AuthenticatedUser) auth.getPrincipal()).getEmail();
        }
        return null;
    }
}
