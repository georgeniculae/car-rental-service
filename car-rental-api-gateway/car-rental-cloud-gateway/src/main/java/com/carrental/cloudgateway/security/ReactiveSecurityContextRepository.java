package com.carrental.cloudgateway.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.util.ObjectUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Mono;

@Configuration
@RequiredArgsConstructor
public class ReactiveSecurityContextRepository implements ServerSecurityContextRepository {

    private static final String SPRING_SECURITY_CONTEXT_ATTR_NAME = "SPRING_SECURITY_CONTEXT";
    private final static String BEARER = "Bearer ";
    private final AuthenticationManager authenticationManager;

    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        return exchange.getSession()
                .doOnNext(webSession -> {
                    if (ObjectUtils.isEmpty(webSession)) {
                        webSession.getAttributes().remove(SPRING_SECURITY_CONTEXT_ATTR_NAME);
                    } else {
                        webSession.getAttributes().put(SPRING_SECURITY_CONTEXT_ATTR_NAME, context);
                    }
                })
                .flatMap(WebSession::changeSessionId);
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        return Mono.justOrEmpty(exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION))
                .filter(authorization -> authorization.startsWith(BEARER))
                .map(token -> token.substring(BEARER.length()))
                .flatMap(token -> Mono.just(new UsernamePasswordAuthenticationToken(token, token)))
                .delayUntil(authenticationManager::authenticate)
                .map(SecurityContextImpl::new);
    }

}
