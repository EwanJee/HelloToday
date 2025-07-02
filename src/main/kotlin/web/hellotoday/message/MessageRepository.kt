package web.hellotoday.message

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface MessageRepository :
    MongoRepository<Message, String>,
    CustomMessageRepository {
    fun findByDateOrderByCreatedAtDesc(date: LocalDate): List<Message>

    fun findByDateOrderByCreatedAtAsc(date: LocalDate): List<Message>

    fun countByDate(date: LocalDate): Long
}
