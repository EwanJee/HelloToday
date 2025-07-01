package web.hellotoday.stats

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import web.hellotoday.stats.dto.DailyStatsListResponse
import web.hellotoday.stats.dto.StatsResponse
import java.time.LocalDate

@Service
@Transactional
class StatsService(
    private val statsRepository: StatsRepository,
) {
    fun incrementTodayCount() {
        val today = LocalDate.now()
        val stats =
            statsRepository.findByDate(today)
                ?: Stats(date = today)

        stats.increment()
        statsRepository.save(stats)
    }

    @Transactional(readOnly = true)
    fun getTodayStats(): StatsResponse? {
        val today = LocalDate.now()
        return statsRepository.findByDate(today)?.let { StatsResponse.from(it) }
    }

    @Transactional(readOnly = true)
    fun getAllStats(): DailyStatsListResponse {
        val allStats = statsRepository.findAllByOrderByDateDesc()
        return DailyStatsListResponse(
            stats = allStats.map { StatsResponse.from(it) },
            totalDays = allStats.size,
        )
    }

    @Transactional(readOnly = true)
    fun getStatsByDate(date: LocalDate): StatsResponse? = statsRepository.findByDate(date)?.let { StatsResponse.from(it) }
}
