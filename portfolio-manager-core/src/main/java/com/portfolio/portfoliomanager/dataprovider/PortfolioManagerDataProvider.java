package com.portfolio.portfoliomanager.dataprovider;

import com.portfolio.portfoliomanager.domain.Asset;
import com.portfolio.portfoliomanager.domain.DigitalUser;
import com.portfolio.portfoliomanager.dto.AssetCreate;
import com.portfolio.portfoliomanager.dto.DigitalUserCreate;
import com.portfolio.portfoliomanager.usecases.asset.ListAssetsUseCase;

import java.util.List;

public interface PortfolioManagerDataProvider {

    DigitalUser createDigitalUser(DigitalUserCreate digitalUserCreate);

    DigitalUser getDigitalUserBySubAndIdPAndTenant(
            String sub,
            DigitalUser.IdentityProviderInformation.IdentityProvider idP,
            String tenantId
    );

    boolean digitalUserExistsBySubAndIdPAndTenantId(
            String sub,
            DigitalUserCreate.IdentityProviderInformation.IdentityProvider idP,
            String tenantId
    );

    void deleteDigitalUser(String digitalUserId);

    Asset createAsset(AssetCreate assetCreate, String digitalUserId);

    List<Asset> listAssets(ListAssetsUseCase.Input input);

    void deleteAsset(String digitalUserId, String assetExternalId);

    boolean assetExistsByExternalId(String externalId);

    Asset findAssetByExternalId(String digitalUserId, String externalId);
}
