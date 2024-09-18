package com.portfolio.portfolio_manager.dataprovider;

import com.portfolio.portfolio_manager.domain.Asset;
import com.portfolio.portfolio_manager.domain.DigitalUser;
import com.portfolio.portfolio_manager.dto.AssetCreate;
import com.portfolio.portfolio_manager.dto.DigitalUserCreate;
import com.portfolio.portfolio_manager.usecases.ListAssetsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PortfolioManagerDataProviderNoSql implements PortfolioManagerDataProvider {

    private final MongoTemplate mongoTemplate;

    @Override
    public DigitalUser createDigitalUser(DigitalUserCreate digitalUserCreate) {
        return null;
    }

    @Override
    public DigitalUser getBySubAndIdP(String sub, DigitalUser.IdentityProviderInformation.IdentityProvider idP) {
        return null;
    }

    @Override
    public void deleteDigitalUser(String id) {

    }

    @Override
    public Asset createAsset(AssetCreate assetCreate, String digitalUserId) {
        return null;
    }

    @Override
    public List<Asset> listAssets(ListAssetsUseCase.Input input) {
        return null;
    }

    @Override
    public void deleteAsset(String assetId) {

    }
}
