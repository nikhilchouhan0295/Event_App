package com.demo.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import com.demo.filter.JwtAuthenticationFilter;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

	@Autowired
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

	@Autowired
    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
            .authorizeExchange(exchanges -> exchanges
            		.pathMatchers( "/payment-gatway/paymentApi/createUserAccout").permitAll()
            		.pathMatchers( "/eventlisting/Event/allEvents").permitAll()
           		.pathMatchers( "/user-service/auth/**").permitAll()
            	.pathMatchers("/eventlisting/Event/add","/eventlisting/Event/update","/eventlisting/Event/delete/**").hasRole("ROLE_admin")
                .pathMatchers( "/bookingservice/**").hasAnyRole("ROLE_user","ROLE_admin")
                .anyExchange().authenticated()              // All other routes require authentication
            )
            .addFilterAt(jwtAuthenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION) // Add JWT filter
            .csrf().disable()  // Disable CSRF for stateless APIs
            .build();
    }
}
