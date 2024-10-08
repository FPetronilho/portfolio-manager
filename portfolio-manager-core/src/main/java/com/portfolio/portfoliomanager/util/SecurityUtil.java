package com.portfolio.portfoliomanager.util;

import com.portfolio.portfoliomanager.exception.InternalServerErrorException;
import com.portfolio.portfoliomanager.security.context.DigitalUserSecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    private SecurityUtil() {
        throw new IllegalStateException("Cannot instantiate a util class");
    }

    public static DigitalUserSecurityContext getDigitalUserSecurityContext() {
        Object principal = SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        if (principal instanceof DigitalUserSecurityContext) {
            // now I can safely cast because the data type is assured by the if statement
            return (DigitalUserSecurityContext) principal;
        }

        throw new InternalServerErrorException("could not retrieve Digital User from Spring Security Context");
    }

}
