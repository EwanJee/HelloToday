package web.hellotoday.common.dto

import java.time.LocalDate

data class AvailableDatesResponse(
    val dates: List<LocalDate>,
    val totalDays: Int,
) {
    companion object {
        fun from(dates: List<LocalDate>): AvailableDatesResponse =
            AvailableDatesResponse(
                dates = dates,
                totalDays = dates.size,
            )
    }
}

data class DateStatsResponse(
    val date: LocalDate,
    val messageCount: Long,
    val hasMessages: Boolean,
) {
    companion object {
        fun create(
            date: LocalDate,
            messageCount: Long,
        ): DateStatsResponse =
            DateStatsResponse(
                date = date,
                messageCount = messageCount,
                hasMessages = messageCount > 0,
            )
    }
}
