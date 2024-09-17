package com.portfolio.portfolio_manager.dataprovider;

import com.portfolio.portfolio_manager.domain.Asset;
import com.portfolio.portfolio_manager.domain.DigitalUser;
import com.portfolio.portfolio_manager.dto.AssetCreate;
import com.portfolio.portfolio_manager.dto.DigitalUserCreate;
import com.portfolio.portfolio_manager.usecases.ListAssetsUseCase;

import java.util.List;

public interface PortfolioManagerDataProvider {

    DigitalUser createDigitalUser(DigitalUserCreate digitalUserCreate);

    void deleteDigitalUser(String id);

    Asset createAsset(String digitalUserId, AssetCreate assetCreate);

    List<Asset> listAssets(ListAssetsUseCase.Input input);

    void deleteAsset(String assetId);
}
