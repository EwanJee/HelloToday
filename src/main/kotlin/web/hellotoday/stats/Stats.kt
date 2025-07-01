package web.hellotoday.stats

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate
import java.time.LocalDateTime

@Document(collection = "stats")
class Stats(
    @Id
    val id: String? = null,
    @Indexed(unique = true)
    val date: LocalDate,
    var count: Long = 0,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    var updatedAt: LocalDateTime = LocalDateTime.now(),
) {
    fun increment() {
        count++
        updatedAt = LocalDateTime.now()
    }
}
