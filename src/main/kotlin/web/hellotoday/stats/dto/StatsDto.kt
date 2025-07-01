package web.hellotoday.stats.dto

import web.hellotoday.stats.Stats
import java.time.LocalDate
import java.time.LocalDateTime

class StatsDto

data class StatsResponse(
    val date: LocalDate,
    val count: Long,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
) {
    companion object {
        fun from(stats: Stats): StatsResponse =
            StatsResponse(
                date = stats.date,
                count = stats.count,
                createdAt = stats.createdAt,
                updatedAt = stats.updatedAt,
            )
    }
}

data class DailyStatsListResponse(
    val stats: List<StatsResponse>,
    val totalDays: Int,
)
