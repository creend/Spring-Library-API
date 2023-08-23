package com.library.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {
    @Bean
    public InMemoryUserDetailsManager userDetailsManager(){
         UserDetails moderator = User.builder().username("moderator").password("{noop}moderator").roles("MODERATOR").build();
         UserDetails admin = User.builder().username("admin").password("{noop}admin").roles("MODERATOR","ADMIN").build();

         return new InMemoryUserDetailsManager(moderator,admin);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((requests) -> requests
                        .requestMatchers(HttpMethod.POST).hasRole("MODERATOR")
                .requestMatchers(HttpMethod.PATCH).hasRole("MODERATOR")
                .requestMatchers(HttpMethod.DELETE).hasRole("ADMIN")
              .requestMatchers(HttpMethod.GET).permitAll()
        );
        http.httpBasic(Customizer.withDefaults());
        http.csrf(AbstractHttpConfigurer::disable);

       return http.build();
    }
}
