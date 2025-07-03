package web.hellotoday.websocket

import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer

@Configuration
@EnableWebSocketMessageBroker
class WebSocketConfig : WebSocketMessageBrokerConfigurer {
    override fun configureMessageBroker(config: MessageBrokerRegistry) {
        // 클라이언트로 메시지를 보낼 때 사용할 prefix
        config.enableSimpleBroker("/topic")
        // 클라이언트에서 서버로 메시지를 보낼 때 사용할 prefix
        config.setApplicationDestinationPrefixes("/app")
    }

    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        // WebSocket 연결 엔드포인트 - SockJS 활성화
        registry
            .addEndpoint("/ws/**")
            .setAllowedOriginPatterns(
                "http://localhost:*",
                "https://localhost:*",
                "https://*.vercel.app",
                "https://*.railway.app",
                "https://hello-today-frontend.vercel.app",
                "https://hello-today-frontend-*.vercel.app",
            ) // 모든 도메인 허용
            .withSockJS()
    }
}
