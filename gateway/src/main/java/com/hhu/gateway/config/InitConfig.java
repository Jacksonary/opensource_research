package com.hhu.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author jacks
 * @date 2022/6/24
 */
@Configuration
public class InitConfig {

    /**
     * 1. 简单场景，将对应 uri 路由至指定域上
     *
     * 如: 请求localhost:8081/ping -> localhost:8200/ping
     */
    // @Bean
    // public RouteLocator myRoutes(RouteLocatorBuilder builder) {
    // return builder.routes()
    // .route(p -> p
    // .path("/ping")
    // .filters(f -> f.addRequestHeader("Hello", "World"))
    //// .uri("http://httpbin.org:80"))
    //// .uri("http://www.baidu.com:80"))
    // .uri("http://localhost:8200"))
    // .build();
    // }

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
            // 将指定的域转到指定的域上，并且加装 circuit breaker
            .route(p -> p.host("*.circuitbreaker.com")
                .filters(f ->
                        // circuit breaker 可以将响应慢的请求路由到一些默认的api上
                        f.circuitBreaker(config -> config.setName("mycmd")
                        // 超时路由到指定路径
                        .setFallbackUri("forward:/fallback"))
                )
                .uri("http://httpbin.org:80"))

            .build();
    }
}
