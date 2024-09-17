package com.portfolio.portfolio_manager.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Asset extends BaseObject {

    private String externalId; // ID from the source system (e.g. ID from expense-tracker)
    private String type; // e.g. expense
    private ArtifactInformation artifactInfo;
    private PermissionPolicy permissionPolicy;

    @ToString
    @Getter
    @RequiredArgsConstructor
    public enum PermissionPolicy {

        OWNER("owner"), // digital user owns this asset
        VIEWER("viewer"); // digital user can only view this asset

        private final String value;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ArtifactInformation {

        private String groupId;
        private String artifactId;
        private String version;
    }
}
