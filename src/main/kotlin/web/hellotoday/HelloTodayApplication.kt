package web.hellotoday

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class HelloTodayApplication

fun main(args: Array<String>) {
    runApplication<HelloTodayApplication>(*args)
}
