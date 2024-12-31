package com.demo.filter;import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import com.demo.util.JwtUtil;

import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.stream.Collectors;

@Component
public class JwtAuthenticationFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String authorizationHeader = exchange.getRequest()
                .getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);

            try {
                System.out.println("Token: " + token);

                // Parse and validate token
                var claims = JwtUtil.extractClaims(token);
                System.out.println("Claims: " + claims);

                if (!JwtUtil.isTokenExpired(claims)) {
                    String id = JwtUtil.getUsername(claims);
                    String role = JwtUtil.getRoles(claims);
                    System.out.println("Role: " + role);

                    // Build authorities
                    var authorities = Arrays.stream(new String[]{role})
                            .map(r -> new SimpleGrantedAuthority("ROLE_" + r))
                            .collect(Collectors.toList());

                    // Create authentication token and security context
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(id, null, authorities);
                    
                    SecurityContext context = new SecurityContextImpl(authenticationToken);

                    // Wrap request to add the custom header
                    ServerHttpRequest mutatedRequest = new ServerHttpRequestDecorator(exchange.getRequest()) {
                        @Override
                        public HttpHeaders getHeaders() {
                            HttpHeaders headers = new HttpHeaders();
                            headers.putAll(super.getHeaders()); // Copy existing headers
                            headers.add("loggedInUser", id); // Add custom header
                            return headers;
                        }
                    };

                    ServerWebExchange mutatedExchange = exchange.mutate()
                            .request(mutatedRequest)
                            .build();

                    // Proceed with the modified exchange and security context
                    return chain.filter(mutatedExchange)
                            .contextWrite(ReactiveSecurityContextHolder.withSecurityContext(Mono.just(context)));
                }
            } catch (Exception e) {
                e.printStackTrace();
                return Mono.error(new RuntimeException("Invalid JWT token"));
            }
        }

        // Proceed without modifications if no authorization header
        return chain.filter(exchange);
    }
}
