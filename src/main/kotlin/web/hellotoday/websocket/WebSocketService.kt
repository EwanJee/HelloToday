package web.hellotoday.websocket

import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service
import web.hellotoday.message.dto.MessageResponse

@Service
class WebSocketService(
    private val messagingTemplate: SimpMessagingTemplate,
) {
    fun broadcastNewMessage(message: MessageResponse) {
        // 모든 구독자에게 새 메시지 전송
        messagingTemplate.convertAndSend("/topic/messages", message)
    }

    fun broadcastDailyReset() {
        // 하루 리셋 알림
        messagingTemplate.convertAndSend("/topic/reset", "새로운 하루가 시작되었습니다!")
    }
}
