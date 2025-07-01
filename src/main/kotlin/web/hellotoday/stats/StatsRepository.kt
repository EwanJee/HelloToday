package web.hellotoday.stats

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface StatsRepository : MongoRepository<Stats, String> {
    fun findByDate(date: LocalDate): Stats?

    fun findAllByOrderByDateDesc(): List<Stats>

    fun deleteByDate(date: LocalDate): Long
}
