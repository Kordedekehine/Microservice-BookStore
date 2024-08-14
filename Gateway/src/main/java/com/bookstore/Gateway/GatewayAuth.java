package com.bookstore.Gateway;


import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collections;

@Component
public class GatewayAuth implements ReactiveAuthenticationManager {

    /**
     *  The ReactiveAuthenticationManager interface is responsible for authenticating a user based on the
     *  provided credentials. It is used in reactive(non-blocking) applications, for non-reactive(blocking)
     *  application we can use AuthenticationManager interface. It is a key component in the authentication process.
     * @param authentication
     * @return
     */

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {

        //checks the provided username and password against hardcoded values
        //and return the authenticated user hence the use of Mono return type and not flux
        //On successful authentication, we can return object of UsernamePasswordAuthenticationToken
        final String name = authentication.getName();
        final String password = authentication.getCredentials().toString();
        if (("korede".equals(name) && "korede".equals(password))) {
            return Mono.just(new UsernamePasswordAuthenticationToken(name, password, Collections.emptyList()));
        }
        return null;
    }

}
