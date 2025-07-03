package web.hellotoday.common.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
class SecurityConfig {
    @Value("\${spring.profiles.active}")
    private lateinit var profile: String

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() } // REST API에서 CSRF 비활성화
            .cors { it.configurationSource(corsConfigurationSource()) } // CORS 설정
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) } // 세션 사용 안함
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers("/api/**")
                    .permitAll() // API 엔드포인트 허용
                    .requestMatchers("/api/**/**")
                    .permitAll() // API 하위 엔드포인트 허용
                    .requestMatchers("/ws/**")
                    .permitAll() // WebSocket 엔드포인트 허용
                    .requestMatchers("/ws-native/**")
                    .permitAll() // 순수 WebSocket 엔드포인트 허용
                    .requestMatchers("/", "/static/**", "/public/**")
                    .permitAll() // 정적 리소스 허용
                    .requestMatchers("/actuator/health")
                    .permitAll() // 헬스체크 허용
                    .anyRequest()
                    .authenticated() // 나머지 요청은 인증 필요
            }.headers { headers ->
                headers
                    .frameOptions { it.sameOrigin() }
                    .contentTypeOptions { }
                    .httpStrictTransportSecurity { hstsConfig ->
                        if (isProduction()) {
                            hstsConfig
                                .maxAgeInSeconds(31536000) // 1년
                                .includeSubDomains(true) // 서브도메인 포함
                                .preload(true)
                        } else {
                            hstsConfig.disable() // 개발환경에서는 비활성화
                        }
                    }
            }

        return http.build()
    }

    private fun isProduction(): Boolean {
        // 환경 변수나 프로퍼티를 통해 프로덕션 환경 여부를 판단
        println("--- Current Profile: $profile --")
//        return profile == "prod" || profile == "production"
        return profile.contains("prod") || profile.contains("production")
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()

        // Vue.js 개발 서버 및 배포 서버 허용
        configuration.allowedOriginPatterns =
            listOf(
                "http://localhost:*",
                "https://localhost:*",
                "https://*.vercel.app",
                "https://*.railway.app",
                "https://hello-today-frontend.vercel.app",
                "https://hello-today-frontend-*.vercel.app",
            )
        configuration.allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "OPTIONS")
        configuration.allowedHeaders = listOf("*")
        configuration.allowCredentials = true
        configuration.maxAge = 3600L

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)

        return source
    }
}
