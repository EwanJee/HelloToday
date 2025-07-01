package web.hellotoday.message

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate
import java.time.LocalDateTime

@Document(collection = "messages")
class Message(
    @Id
    val id: String? = null,
    val content: String,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    @Indexed
    val date: LocalDate = LocalDate.now(),
)
