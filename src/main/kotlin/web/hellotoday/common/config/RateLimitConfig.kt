package web.hellotoday.common.config

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.filter.OncePerRequestFilter
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger

@Configuration
class RateLimitConfig {
    // 간단한 Rate Limiting
    private val requestCounts = ConcurrentHashMap<String, AtomicInteger>()
    private val lastResetTime = ConcurrentHashMap<String, LocalDateTime>()

    @Bean
    fun rateLimitFilter(): OncePerRequestFilter {
        return object : OncePerRequestFilter() {
            override fun doFilterInternal(
                request: HttpServletRequest,
                response: HttpServletResponse,
                filterChain: FilterChain,
            ) {
                // POST 요청에 대해서만 Rate Limiting 적용
                if (request.method == "POST" && request.requestURI.startsWith("/api/messages")) {
                    val clientIp = getClientIp(request)

                    if (isRateLimited(clientIp)) {
                        response.status = 429
                        // 메세지가 ?로 뜬다
                        response.characterEncoding = "UTF-8"
                        response.contentType = "application/json; charset=UTF-8"
                        response.writer.write("{\"success\":false,\"message\":\"1분당 10회 씩 까지 메시지 전송이 가능합니다.\"}")
                        return
                    }
                }

                filterChain.doFilter(request, response)
            }

            private fun isRateLimited(clientIp: String): Boolean {
                val now = LocalDateTime.now()
                val count = requestCounts.computeIfAbsent(clientIp) { AtomicInteger(0) }
                val lastReset = lastResetTime.computeIfAbsent(clientIp) { now }

                // 1분마다 리셋
                if (ChronoUnit.MINUTES.between(lastReset, now) >= 1) {
                    count.set(0)
                    lastResetTime[clientIp] = now
                }

                // 분당 10개 메시지 제한
                return count.incrementAndGet() > 10
            }

            private fun getClientIp(request: HttpServletRequest): String =
                request.getHeader("X-Forwarded-For")
                    ?: request.getHeader("X-Real-IP")
                    ?: request.remoteAddr
        }
    }
}
