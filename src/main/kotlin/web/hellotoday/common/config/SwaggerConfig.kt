package web.hellotoday.common.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.filter.ForwardedHeaderFilter

@Configuration
class SwaggerConfig {
    @Bean
    fun forwardHeadFilter(): ForwardedHeaderFilter = ForwardedHeaderFilter()
}
