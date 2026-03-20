package br.com.lbenaducci.meowth.platform.config

import br.com.lbenaducci.meowth.shared.domain.exceptions.DomainException
import br.com.lbenaducci.meowth.shared.domain.exceptions.RepositoryException
import br.com.lbenaducci.meowth.shared.domain.exceptions.ValidationException
import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.servlet.http.HttpServletRequest
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.util.HtmlUtils
import java.time.Instant

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(DomainException::class)
    fun handleDomainException(ex: DomainException, request: HttpServletRequest): ResponseEntity<ErrorResponse> {
        return ResponseEntity.unprocessableContent()
            .body(errorResponse(ex, request, HttpStatus.UNPROCESSABLE_ENTITY))
    }

    @ExceptionHandler(ValidationException::class)
    fun handleValidationException(ex: ValidationException, request: HttpServletRequest): ResponseEntity<ErrorResponse> {
        return ResponseEntity.badRequest()
            .body(errorResponse(ex, request, HttpStatus.BAD_REQUEST))
    }

    @ExceptionHandler(RepositoryException::class)
    fun handleRepositoryException(ex: RepositoryException, request: HttpServletRequest): ResponseEntity<ErrorResponse> {
        return ResponseEntity.internalServerError()
            .body(errorResponse(ex, request, HttpStatus.INTERNAL_SERVER_ERROR))
    }

    private fun errorResponse(ex: DomainException, request: HttpServletRequest, status: HttpStatus): ErrorResponse {
        return ErrorResponse(
            status = status.name,
            path = HtmlUtils.htmlEscape(request.requestURI),
            code = ex.code,
            title = status.reasonPhrase,
            message = HtmlUtils.htmlEscape(ex.error.defaultMessage),
            params = ex.params
        )
    }

    data class ErrorResponse(
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS", timezone = "UTC")
        val timestamp: Instant = Instant.now(),
        val status: String,
        val path: String,
        val code: String,
        val title: String,
        val message: String,
        val params: Map<String, Any> = emptyMap()
    )
}