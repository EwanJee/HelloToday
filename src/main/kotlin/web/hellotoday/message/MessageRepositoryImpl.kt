package web.hellotoday.message

import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class MessageRepositoryImpl(
    private val mongoTemplate: MongoTemplate,
) : CustomMessageRepository {
    override fun findDistinctDates(): List<LocalDate> =
        mongoTemplate
            .query(Message::class.java)
            .distinct("date") // 명시적으로 String 지정
            .all()
            .map { LocalDate.parse(it.toString()) }
}

interface CustomMessageRepository {
    fun findDistinctDates(): List<LocalDate>
}
