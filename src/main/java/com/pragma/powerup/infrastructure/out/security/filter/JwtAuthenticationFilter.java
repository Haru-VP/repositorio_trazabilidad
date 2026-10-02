package com.pragma.powerup.infrastructure.out.security.filter;

import com.pragma.powerup.infrastructure.out.security.adapter.JwtAdapter;
import com.pragma.powerup.infrastructure.out.security.model.AuthenticatedUser;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String BEARER_PREFIX = "Bearer ";
    private final JwtAdapter jwtAdapter;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader != null && authHeader.startsWith(BEARER_PREFIX)) {
            String token = authHeader.substring(BEARER_PREFIX.length());

            if (jwtAdapter.esTokenValido(token)) {
                Claims claims = jwtAdapter.extraerClaims(token);
                String email = claims.getSubject();
                String role = claims.get("rol", String.class);
                Long id = null;

                Object idObj = claims.get("id");
                if (idObj instanceof Integer) {
                    id = ((Integer) idObj).longValue();
                } else if (idObj instanceof Long) {
                    id = (Long) idObj;
                } else if (idObj instanceof String) {
                    try {
                        id = Long.parseLong((String) idObj);
                    } catch (NumberFormatException ignored) {
                        // ignore parsing error
                    }
                }

                if (email != null && role != null) {
                    String authorityRole = role.startsWith("ROLE_") ? role : "ROLE_" + role;
                    AuthenticatedUser principal = new AuthenticatedUser(id, email, role);
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    principal,
                                    null,
                                    Collections.singletonList(new SimpleGrantedAuthority(authorityRole))
                            );
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
