@file:Suppress("ktlint:standard:no-wildcard-imports")

package web.hellotoday.stats

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import web.hellotoday.common.ApiResponse
import web.hellotoday.stats.dto.DailyStatsListResponse
import web.hellotoday.stats.dto.StatsResponse
import java.time.LocalDate

@RestController
@RequestMapping("/api/stats")
class StatsController(
    private val statsService: StatsService,
) {
    @GetMapping("/today")
    fun getTodayStats(): ResponseEntity<ApiResponse<StatsResponse?>> {
        val response = statsService.getTodayStats()
        return ResponseEntity.ok(ApiResponse.success(response))
    }

    @GetMapping("/all")
    fun getAllStats(): ResponseEntity<ApiResponse<DailyStatsListResponse>> {
        val response = statsService.getAllStats()
        return ResponseEntity.ok(ApiResponse.success(response))
    }

    @GetMapping("/date/{date}")
    fun getStatsByDate(
        @PathVariable date: LocalDate,
    ): ResponseEntity<ApiResponse<StatsResponse?>> {
        val response = statsService.getStatsByDate(date)
        return ResponseEntity.ok(ApiResponse.success(response))
    }
}
