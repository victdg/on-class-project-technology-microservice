package com.example.resilient_api.infrastructure.entrypoints;

import com.example.resilient_api.infrastructure.entrypoints.handler.TechnologyHandler;
import com.example.resilient_api.infrastructure.entrypoints.handler.UserHandlerImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRest {
    @Bean
    public RouterFunction<ServerResponse> routerFunction(UserHandlerImpl userHandler) {
        return route(POST("/user"), userHandler::createUser);
    }

    @Bean
    public RouterFunction<ServerResponse> technologyRouterFunction(TechnologyHandler technologyHandler) {
        return route(POST("/technology"), technologyHandler::saveTechnology);
    }
}
