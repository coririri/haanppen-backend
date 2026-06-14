package com.hanpyeon.academyapi.exception;

import java.util.Arrays;
import org.apache.tomcat.util.http.fileupload.impl.InvalidContentTypeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ApiExceptionHandler {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(InvalidContentTypeException.class)
    public ResponseEntity<ExceptionResponseBody> invalidContentTypeExceptionHandler(final InvalidContentTypeException invalidContentTypeException) {
        ErrorCode errorCode = ErrorCode.INVALID_CONTENT_TYPE;
        logger.warn("[ EXCEPTION WITH ] -> " + invalidContentTypeException.toString());
        return createExceptionResponse(errorCode, ExceptionResponseBody.of(errorCode, invalidContentTypeException.toString()));
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ExceptionResponseBody> mediaTypeNotSupportedExceptionHandler(final HttpMediaTypeNotSupportedException exception) {
        logger.warn("[ EXCEPTION WITH ] -> " + exception.getMessage());
        return createExceptionResponse(ErrorCode.INVALID_CONTENT_TYPE, ExceptionResponseBody.of(ErrorCode.INVALID_CONTENT_TYPE));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionResponseBody> requestFormatExceptionHandler(final HttpMessageNotReadableException exception) {
        ErrorCode errorCode = ErrorCode.HTTP_MESSAGE_NOT_READABLE;
        logger.warn("[ EXCEPTION WITH ] -> " + exception.getMessage());
        return createExceptionResponse(errorCode, ExceptionResponseBody.of(errorCode, exception.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponseBody> missingFieldExceptionHandler(final MethodArgumentNotValidException exception) {
        ErrorCode errorCode = ErrorCode.METHOD_ARGUMENT_NOT_VALID_EXCEPTION;
        List<String> errors = exception.getFieldErrors().stream()
                .map(fieldError -> "[ " + fieldError.getField() + " ]" + " : " + fieldError.getDefaultMessage())
                .toList();
        logger.warn("[ EXCEPTION WITH ] -> " + exception.getMessage());
        return createExceptionResponse(errorCode, ExceptionResponseBody.of(errorCode, errors));
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ExceptionResponseBody> businessExceptionHandler(final BusinessException businessException) {
        ErrorCode errorCode = businessException.getErrorCode();
        String details = businessException.getMessage();
//
//        logger.debug("[ EXCEPTION WITH ] -> {} : {}", errorCode, details);
//        logger.debug("[ STACK TRACE ] -> {} ", Arrays.toString(businessException.getStackTrace()));
        return createExceptionResponse(errorCode, ExceptionResponseBody.of(errorCode, details));
    }

    private ResponseEntity<ExceptionResponseBody> createExceptionResponse(ErrorCode errorCode, ExceptionResponseBody exceptionResponseBody) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(exceptionResponseBody);
    }
}
