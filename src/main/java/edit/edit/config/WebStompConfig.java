package edit.edit.config;

import edit.edit.config.stomp.AgentWebSocketHandlerDecoratorFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebStompConfig implements WebSocketMessageBrokerConfigurer {

	private final AgentWebSocketHandlerDecoratorFactory agentWebSocketHandlerDecoratorFactory;

	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		config.enableSimpleBroker("/sub"); //받기
		config.setApplicationDestinationPrefixes("/pub"); //보내기
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/ws-edit")
				.setAllowedOrigins("http://localhost:3000")
				.withSockJS();
	}

	@Override
	public void configureWebSocketTransport(WebSocketTransportRegistration registry) {
		registry.setDecoratorFactories(agentWebSocketHandlerDecoratorFactory);
	}
}
