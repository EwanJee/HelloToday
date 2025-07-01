package web.hellotoday.schedule

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import web.hellotoday.message.MessageService
import web.hellotoday.stats.StatsService
import web.hellotoday.websocket.WebSocketService
import java.time.LocalDate

@Service
class ScheduledService(
    private val messageService: MessageService,
    private val statsService: StatsService,
    private val webSocketService: WebSocketService,
) {
    // 매일 자정에 실행 - 새로운 하루 시작 알림만
    @Scheduled(cron = "0 0 0 * * *")
    fun notifyNewDay() {
        val today = LocalDate.now()

        // 클라이언트에게 새로운 하루 시작 알림
        webSocketService.broadcastDailyReset()
    }
}
