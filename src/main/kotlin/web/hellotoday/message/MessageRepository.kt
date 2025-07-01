package web.hellotoday.message

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface MessageRepository : MongoRepository<Message, String> {
    fun findByDateOrderByCreatedAtDesc(date: LocalDate): List<Message>

    fun findByDateOrderByCreatedAtAsc(date: LocalDate): List<Message>

    fun countByDate(date: LocalDate): Long

    // 메시지가 있는 날짜들만 조회 (내림차순)
    @Query(value = "{}", fields = "{'date': 1}")
    fun findDistinctDatesRaw(): List<Message>

    fun findDistinctDates(): List<LocalDate> =
        findDistinctDatesRaw()
            .map { it.date }
            .distinct()
            .sortedDescending()
}
