package com.portfolio.portfoliomanager.exception;

public class ConfigurationErrorException extends BusinessException {

    public ConfigurationErrorException(String message) {
        super(
                ExceptionCode.CONFIGURATION_ERROR,
                message
        );
    }
}
