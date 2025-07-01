package web.hellotoday.message

import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import web.hellotoday.common.ApiResponse
import web.hellotoday.message.dto.DailyMessageResponse
import web.hellotoday.message.dto.MessageRequest
import web.hellotoday.message.dto.MessageResponse
import java.time.LocalDate

@RestController
@RequestMapping("/api/messages")
@CrossOrigin(origins = ["*"])
class MessageController(
    private val messageService: MessageService,
) {
    @PostMapping
    fun create(
        @Valid @RequestBody request: MessageRequest,
    ): ResponseEntity<ApiResponse<MessageResponse>> {
        val response = messageService.saveMessage(request)
        return ResponseEntity.ok(ApiResponse.success(response, "메시지가 성공적으로 저장되었습니다."))
    }

    @GetMapping("/today")
    fun getTodayMessages(): ResponseEntity<ApiResponse<DailyMessageResponse>> {
        val response = messageService.getTodayMessages()
        return ResponseEntity.ok(ApiResponse.success(response))
    }

    @GetMapping("/date/{date}")
    fun getMessagesByDate(
        @PathVariable date: LocalDate,
    ): ResponseEntity<ApiResponse<DailyMessageResponse>> {
        val response = messageService.getMessagesByDate(date)
        return ResponseEntity.ok(ApiResponse.success(response))
    }

    @GetMapping("/dates")
    fun getAvailableDates(): ResponseEntity<ApiResponse<List<LocalDate>>> {
        val dates = messageService.getAvailableDates()
        return ResponseEntity.ok(ApiResponse.success(dates))
    }
}
