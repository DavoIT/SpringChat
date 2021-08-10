package com.daves.chat.configurer;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

//TODO WHAT THE HELL IS THIS CLASS, HOLY MONKEY!
//@Configuration
//@EnableWebSocketMessageBroker
//public class WsConfig implements WebSocketMessageBrokerConfigurer {
//
//	@Override
//	public void registerStompEndpoints(StompEndpointRegistry registry) {
//		registry.addEndpoint("/my_endpoint");
//		registry.addEndpoint("/my_endpoint").withSockJS();
//	}
//
//	@Override
//	public void configureMessageBroker(MessageBrokerRegistry registry) {
//		registry.enableSimpleBroker("queue");
//		registry.setApplicationDestinationPrefixes("/app");
//	}
//}
