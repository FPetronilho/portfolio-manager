package com.portfolio.portfoliomanager.security.context;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DigitalUserSecurityContext {

    private String id;
    private String subject;
}
