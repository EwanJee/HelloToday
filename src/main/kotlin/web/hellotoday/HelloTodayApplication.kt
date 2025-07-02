package web.hellotoday

import io.github.cdimascio.dotenv.dotenv
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class HelloTodayApplication

fun main(args: Array<String>) {
    val dotenv = dotenv()
    dotenv["MONGO_USERNAME"]?.let { System.setProperty("MONGO_USERNAME", it) }
    dotenv["MONGO_PASSWORD"]?.let { System.setProperty("MONGO_PASSWORD", it) }
    dotenv["MONGO_URI"]?.let { System.setProperty("MONGO_URI", it) }
    runApplication<HelloTodayApplication>(*args)
}
