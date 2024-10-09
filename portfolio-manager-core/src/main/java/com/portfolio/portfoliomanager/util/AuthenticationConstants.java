package com.portfolio.portfoliomanager.util;

import lombok.*;
import org.springframework.http.HttpMethod;

public class AuthenticationConstants {

    public AuthenticationConstants() {
        throw new IllegalStateException("Cannot instantiate an util class.");
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Authentication {

        public static final String HTTP_HEADER_AUTHORIZATION = "Authorization";

        @Getter
        @ToString
        @RequiredArgsConstructor
        public enum Scope {

            DIGITAL_USER_CREATE("DIGITAL_USER_CREATE", HttpMethod.POST, "/api/v1/digitalUsers");

            private final String value;
            private final HttpMethod httpMethod;
            private final String uri;
        }

        @NoArgsConstructor(access = AccessLevel.PRIVATE)
        public static class Jwt {

            public static final int ALGORITHM_KEY_SIZE_BYTES = 64;
            public static final String PREFIX = "Bearer ";
            public static final String ISSUER = "auth8";
            public static final int ALLOWED_CLOCK_SKEW_SECONDS = 30;
            public static final String AUDIENCE = "portfolio-manager";

            @Getter
            @ToString
            @RequiredArgsConstructor
            public enum Claim {

                SCOPE("scope"),
                DIGITAL_USER_ID("digitalUserId");

                private final String value;
            }
        }
    }
}
