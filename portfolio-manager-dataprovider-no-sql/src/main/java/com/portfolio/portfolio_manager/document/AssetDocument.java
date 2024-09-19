package com.portfolio.portfolio_manager.document;

import com.portfolio.portfolio_manager.domain.Asset;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
@Document(collection = "asset")
public class AssetDocument extends BaseDocument {

    @Indexed(unique = true)
    private String id;

    private String externalId;
    private String type;
    private Asset.ArtifactInformation artifactInfo;
    private Asset.PermissionPolicy permissionPolicy;
}
