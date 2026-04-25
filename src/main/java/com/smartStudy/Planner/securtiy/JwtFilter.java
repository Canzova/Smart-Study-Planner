package com.smartStudy.Planner.securtiy;

import com.smartStudy.Planner.entity.User;
import com.smartStudy.Planner.repositories.UserRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final AuthUtil authUtil;
    private final UserRepository userRepository;
    private final HandlerExceptionResolver exceptionResolver;

    // We cannot use @RequiredArgsConstructor here because @Qualifier needs
    // to be on the constructor parameter — Lombok can't do that for us.
    // So we write the constructor manually.
    public JwtFilter(AuthUtil authUtil,
                     UserRepository userRepository,
                     @Qualifier("handlerExceptionResolver") HandlerExceptionResolver exceptionResolver) {
        this.authUtil = authUtil;
        this.userRepository = userRepository;
        this.exceptionResolver = exceptionResolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            // Step 1 : Get the token from the header
            String authorizationHeader = request.getHeader("Authorization");

            // If header does not have authorization token then go to next filter in the filter chain
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }

            String token = authorizationHeader.split("Bearer ")[1];

            // Step 2 : Get the username from token
            String username = authUtil.getUsernameFromToken(token);

            // Step 3 : If the token was valid then the username should != null
            // and the user is not already present in the security context holder
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                User user = userRepository.findByUsername(username).orElseThrow();

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            // Step 4. Go to next filter
            filterChain.doFilter(request, response);

        } catch (MalformedJwtException | ExpiredJwtException e) {
            exceptionResolver.resolveException(request, response, null, e);
        } catch (Exception e) {
            exceptionResolver.resolveException(request, response, null, e);
        }
    }
}