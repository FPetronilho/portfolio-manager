package com.portfolio.portfoliomanager.exception;

import com.portfolio.portfoliomanager.mapper.ExceptionMapperEntryPointRest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class RestExceptionHandler {

    private final ExceptionMapperEntryPointRest mapper;

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ExceptionDto> handleBusinessException(BusinessException e) {
        ExceptionDto exceptionDto = mapper.toExceptionDto(e);
        return new ResponseEntity<>(
                exceptionDto,
                new HttpHeaders(),
                HttpStatus.valueOf(exceptionDto.getHttpStatusCode())
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDto> handleGlobalException(Exception e) {
        return handleBusinessException(
                new BusinessException(ExceptionCode.INTERNAL_SERVER_ERROR, e.getMessage())
        );
    }
}
