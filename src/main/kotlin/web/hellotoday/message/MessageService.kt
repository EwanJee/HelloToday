package web.hellotoday.message

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import web.hellotoday.message.dto.DailyMessageResponse
import web.hellotoday.message.dto.MessageRequest
import web.hellotoday.message.dto.MessageResponse
import web.hellotoday.stats.StatsService
import web.hellotoday.websocket.WebSocketService
import java.time.LocalDate

@Service
@Transactional
class MessageService(
    private val messageRepository: MessageRepository,
    private val statsService: StatsService,
    private val webSocketService: WebSocketService,
) {
    fun saveMessage(request: MessageRequest): MessageResponse {
        val message = Message(content = request.content)
        val savedMessage = messageRepository.save(message)

        // 통계 업데이트
        statsService.incrementTodayCount()

        // 실시간으로 모든 클라이언트에게 전송
        val response = MessageResponse.from(savedMessage)
        if (savedMessage.date == LocalDate.now()) {
            webSocketService.broadcastNewMessage(response)
        }

        return response
    }

    @Transactional(readOnly = true)
    fun getTodayMessages(): DailyMessageResponse {
        val today = LocalDate.now()
        return getMessagesByDate(today)
    }

    @Transactional(readOnly = true)
    fun getMessagesByDate(date: LocalDate): DailyMessageResponse {
        val messages = messageRepository.findByDateOrderByCreatedAtAsc(date)
        val totalCount = messageRepository.countByDate(date)

        return DailyMessageResponse(
            date = date,
            messages = messages.map { MessageResponse.from(it) },
            totalCount = totalCount,
        )
    }

    @Transactional(readOnly = true)
    fun getAvailableDates(): List<LocalDate> = messageRepository.findDistinctDates()
}
