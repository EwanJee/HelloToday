package web.hellotoday.common

data class ApiResponse<T>(
    val success: Boolean,
    val data: T?,
    val message: String?,
) {
    companion object {
        fun <T> success(data: T): ApiResponse<T> = ApiResponse(true, data, null)

        fun <T> success(
            data: T,
            message: String,
        ): ApiResponse<T> = ApiResponse(true, data, message)

        fun <T> error(message: String): ApiResponse<T> = ApiResponse(false, null, message)
    }
}
