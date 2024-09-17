package com.portfolio.portfolio_manager.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.portfolio.portfolio_manager.domain.Asset;
import com.portfolio.portfolio_manager.util.Constants;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AssetCreate {

    //TODO: To implement validation it will be necessary to fully replicate the domain and validate each field.

    @NotNull(message = Constants.ASSET_EXTERNAL_ID_MANDATORY_MSG)
    @Pattern(regexp = Constants.ID_REGEX, message = Constants.ASSET_EXTERNAL_ID_INVALID_MSG)
    private String externalId;

    @NotNull(message = Constants.ASSET_TYPE_MANDATORY_MSG)
    @Pattern(regexp = Constants.TYPE_REGEX, message = Constants.TYPE_INVALID_MSG)
    private String type;

    @NotNull(message = Constants.ASSET_ARTIFACT_INFO_MANDATORY_MSG)
    private Asset.ArtifactInformation artifactInfo;

    @NotNull(message = Constants.ASSET_PERMISSION_POLICY_MANDATORY_MSG)
    private Asset.PermissionPolicy permissionPolicy;
}
