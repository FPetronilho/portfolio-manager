package com.portfolio.portfoliomanager.exception;

public class InternalServerErrorException extends BusinessException {

    public InternalServerErrorException(String message) {
        super(
                ExceptionCode.INTERNAL_SERVER_ERROR,
                message
        );
    }
}
