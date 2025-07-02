package web.hellotoday.common.config

import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.model.ChatModel
import org.springframework.ai.openai.OpenAiChatOptions
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

@Configuration
class AiConfig {
    @Value("\${spring.ai.openai.api-key}")
    private lateinit var apiKey: String

    private val baseUrl: String = "https://api.openai.com/v1/chat/completions"

    @Bean
    @Primary
    fun chatClient(chatModel: ChatModel): ChatClient {
        return ChatClient.builder(chatModel)
            .defaultOptions(
                OpenAiChatOptions.builder()
                    .build()
            )
            .build()
    }
}
