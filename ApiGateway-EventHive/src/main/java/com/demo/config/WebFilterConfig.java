package com.demo.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.WebFilter;

import com.demo.filter.JwtAuthenticationFilter;

//@Configuration
public class WebFilterConfig {

    @Bean
    public WebFilter headerModificationFilter() {
        return new JwtAuthenticationFilter();
    }
}