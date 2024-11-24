package com.alphaStore.product.error

import com.alphaStore.product.enums.ResponseType
import com.alphaStore.product.reqres.GenericResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
@RestController
class CustomResponseEntityHandler : ResponseEntityExceptionHandler() {


    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any> {
        var errorMessage = "Bad Req"
        ex.bindingResult.fieldErrors.forEach {
            errorMessage = it.defaultMessage ?: "Bad Req"
        }
        return ResponseEntity(
            GenericResponse(
                code = HttpStatus.BAD_REQUEST.value(),
                message = errorMessage,
                responseType = ResponseType.FAIL
            ),
            HttpStatus.BAD_REQUEST
        )
    }

    override fun handleHttpMessageNotReadable(
        ex: HttpMessageNotReadableException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any> {
        val errorMessage = "Invalid arguments"
        return ResponseEntity(
            GenericResponse(
                code = HttpStatus.BAD_REQUEST.value(),
                message = errorMessage,
                responseType = ResponseType.FAIL
            ),
            HttpStatus.BAD_REQUEST
        )
    }

    override fun handleExceptionInternal(
        ex: java.lang.Exception,
        body: Any?,
        headers: HttpHeaders,
        statusCode: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any> {
        var errorMessage = "Invalid arguments"
        ex.cause?.let { cause ->
            cause.message?.let { message ->
                errorMessage = message
            }
        }
        return ResponseEntity(
            GenericResponse(
                code = HttpStatus.BAD_REQUEST.value(),
                message = errorMessage,
                responseType = ResponseType.FAIL
            ),
            HttpStatus.BAD_REQUEST
        )
    }

}