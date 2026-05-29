package com.smartops.gateway_service.filter;
import com.smartops.gateway_service.security.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

//Global filter for Spring Cloud Gateway that intercepts incoming requests 
//to validate JWT tokens before forwarding them to microservices.
@Component
public class JwtAuthFilter implements GlobalFilter, Ordered {

    private final JwtUtil jwtUtil;

    public JwtAuthFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange,
                             org.springframework.cloud.gateway.filter.GatewayFilterChain chain) {

        String path = exchange.getRequest().getURI().getPath();

        // Allow auth endpoints without token
        if (path.contains("/api/auth/")) {
            return chain.filter(exchange);
        }

        // Extract the Authorization header
        String authHeader = exchange.getRequest()
                .getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);

        // Validate that the header exists and uses the Bearer schema
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        // Remove "Bearer " prefix to isolate the token string
        String token = authHeader.substring(7);

        // Validate token signature and expiration via JwtUtil
        if (!jwtUtil.validateToken(token)) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        // Extract user identity and roles from the token
        Claims claims = jwtUtil.extractClaims(token);

        // Mutate the request to inject user details into headers, 
        // allowing downstream services to identify the user without re-parsing the token
        exchange = exchange.mutate()
                .request(exchange.getRequest()
                        .mutate()
                        .header("X-User", claims.getSubject())
                        .header("X-Role", claims.get("role", String.class))
                        .build())
                .build();

        return chain.filter(exchange);
    }

    //Define the filter order. A value of -1 ensures this runs before 
    //most other filters to perform security checks first.
    @Override
    public int getOrder() {
        return -1;
    }
}