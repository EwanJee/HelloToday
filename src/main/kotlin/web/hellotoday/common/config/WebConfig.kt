package web.hellotoday.common.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig : WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        registry
            .addMapping("/api/**") // CORS를 적용할 경로
            .allowedOriginPatterns(
                "http://localhost:*",
                "https://localhost:*",
                "https://*.vercel.app",
                "https://*.railway.app",
                "https://hello-today-frontend.vercel.app",
                "https://hello-today-frontend-*.vercel.app"
            ) // 특정 도메인 패턴 허용
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
            .allowedHeaders("*")
            .allowCredentials(true)
            .maxAge(3600)
    }
}
