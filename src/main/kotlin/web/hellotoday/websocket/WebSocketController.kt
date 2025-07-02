package web.hellotoday.websocket

import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.messaging.simp.annotation.SubscribeMapping
import org.springframework.stereotype.Controller
import web.hellotoday.message.MessageService
import web.hellotoday.message.dto.DailyMessageResponse

@Controller
class WebSocketController(
    private val messageService: MessageService,
) {
    // 클라이언트가 메시지 채널 구독 시 현재 메시지 목록 전송
    @SubscribeMapping("/topic/messages")
    fun subscribeToMessages(): DailyMessageResponse = messageService.getTodayMessages()

    // 클라이언트가 연결 상태 확인
    @MessageMapping("/ping")
    @SendTo("/topic/pong")
    fun ping(): String = "pong"
}
