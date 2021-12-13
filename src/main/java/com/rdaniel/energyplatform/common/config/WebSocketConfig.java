package com.rdaniel.energyplatform.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/*
    @Configuration - indicates a Spring configuration class
    @EnableWebSocketMessageBroker - enables WebSocket message handling, backed by a message broker
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /*
    Registers the /gs-guide-websocket endpoint, enabling SockJS fallback options so that alternate transports
    can be used if WebSocket is not available.
    SockJS client will attempt to connect to /gs-guide-websocket and use the best available transport:
        - websocket
        - xhr-streaming
        - xhr-pooling
    */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-message").setAllowedOriginPatterns("*").withSockJS();
    }

    /*
    Configures the message broker.
    enableSimpleBroker() - enable a simple memory-based message broker to carry the greeting messages back
                           to the client on destinations prefixed with /topic.
                           Also designate /app prefix for messages that are bound for methods annotated with @MessageMapping
                           e.g. /app/hello - endpoint that the GreetingController.greeting() method is mapped to handle
    */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic");
        registry.setApplicationDestinationPrefixes("/app");
    }
}
