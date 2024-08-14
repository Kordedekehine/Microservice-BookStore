package com.bookstore.Gateway;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfig {

    /**
     * Inside this class,I have a method which is annotated with @Bean annotation & this method will
     * return object of type SecurityWebFilterChain. So ultimately we are creating a bean of type
     * SecurityWebFilterChain. Since Spring cloud gateway works on reactive programming model,
     * we need to create a bean of type SecurityWebFilterChain instead of SecurityFilterChain.
     * In turn,SecurityWebFilterChain method accept parameter of type ServerHttpSecurity,
     * which is used to configure the security rules.
     * ServerHttpSecurity allows you to specify various security configurations such as authentication mechanisms,
     * authorization rules, CSRF protection, session management, and more
     * @param http
     * @return
     */

    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable // Disable CSRF for the specific path
                )
                .authorizeExchange(exchange -> exchange
                        .pathMatchers("/api/v1/book/create").permitAll() //unrestricted access
                        .anyExchange().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .build();
    }


}
