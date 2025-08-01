package web.hellotoday.websocket

import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer
import org.springframework.web.socket.server.support.DefaultHandshakeHandler

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
        // WebSocket 연결 엔드포인트 - Railway 환경 최적화
        registry
            .addEndpoint("/ws")
            .setHandshakeHandler(DefaultHandshakeHandler())
            .setAllowedOriginPatterns(
                "http://localhost:*",
                "https://localhost:*",
                "https://*.vercel.app",
                "https://*.railway.app",
                "https://hello-today-frontend.vercel.app",
                "https://hello-today-frontend-*.vercel.app",
            ).withSockJS()
            .setHeartbeatTime(25000) // 25초마다 heartbeat
            .setDisconnectDelay(5000) // 5초 disconnect delay
            .setHttpMessageCacheSize(1000)
            .setStreamBytesLimit(128 * 1024) // 128KB limit
            .setSessionCookieNeeded(false) // 쿠키 불필요
            .setClientLibraryUrl("https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js")
    }
}
