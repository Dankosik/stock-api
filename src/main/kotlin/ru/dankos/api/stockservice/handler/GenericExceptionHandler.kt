package ru.dankos.api.stockservice.handler

import mu.KLogging
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.util.*

@RestControllerAdvice
class GenericExceptionHandler {

//    @ResponseStatus(INTERNAL_SERVER_ERROR)
//    @ExceptionHandler(value = [Exception::class])
//    internal fun handleAnyException(exception: Exception): ErrorResponse =
//        exception.toErrorResponse(INTERNAL_SERVER_ERROR).apply { logger.error { exception } }
//
//    @ResponseStatus(NOT_FOUND)
//    @ExceptionHandler(value = [StockNotFoundException::class])
//    internal fun handleEntityNotFoundException(exception: StockNotFoundException): ErrorResponse =
//        exception.toErrorResponse(NOT_FOUND).apply { logger.error { exception } }

    private fun Exception.toErrorResponse(httpStatus: HttpStatus): ErrorResponse = ErrorResponse(
        message = this.message,
        timestamp = Date(),
        errorCode = httpStatus.value(),
        errorMessage = httpStatus.name,
    )

    companion object : KLogging()
}
