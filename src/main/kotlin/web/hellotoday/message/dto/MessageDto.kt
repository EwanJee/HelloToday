package web.hellotoday.message.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import web.hellotoday.message.Message
import java.time.LocalDate
import java.time.LocalDateTime

class MessageDto

data class MessageRequest(
    @field:NotBlank(message = "메시지를 입력해주세요")
    @field:Size(max = 500, message = "메시지는 500자 이내로 작성해주세요")
    val content: String,
)

data class MessageResponse(
    val id: String,
    val content: String,
    val createdAt: LocalDateTime,
    val timeAgo: String,
) {
    companion object {
        fun from(message: Message): MessageResponse =
            MessageResponse(
                id = message.id!!,
                content = message.content,
                createdAt = message.createdAt,
                timeAgo = calculateTimeAgo(message.createdAt),
            )

        private fun calculateTimeAgo(createdAt: LocalDateTime): String {
            val now = LocalDateTime.now()
            val duration = java.time.Duration.between(createdAt, now)

            return when {
                duration.toMinutes() < 1 -> "방금 전"
                duration.toMinutes() < 60 -> "${duration.toMinutes()}분 전"
                duration.toHours() < 24 -> "${duration.toHours()}시간 전"
                else -> "${duration.toDays()}일 전"
            }
        }
    }
}

data class DailyMessageResponse(
    val date: LocalDate,
    val messages: List<MessageResponse>,
    val totalCount: Long,
)
