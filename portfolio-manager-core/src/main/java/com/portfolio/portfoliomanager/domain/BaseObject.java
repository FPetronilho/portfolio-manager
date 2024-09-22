package com.portfolio.portfoliomanager.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseObject {

    private String id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
