package com.portfolio.portfolio_manager.exception;

public class ParameterValidationFailedException extends BusinessException {

    public ParameterValidationFailedException(String message) {
        super(
                ExceptionCode.PARAMETER_VALIDATION_ERROR,
                message
        );
    }
}
