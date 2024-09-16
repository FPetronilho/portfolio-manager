package com.portfolio.portfolio_manager.dataprovider;

import com.portfolio.portfolio_manager.domain.Asset;
import com.portfolio.portfolio_manager.domain.DigitalUser;
import com.portfolio.portfolio_manager.dto.AssetCreate;
import com.portfolio.portfolio_manager.dto.DigitalUserCreate;

import java.time.LocalDate;
import java.util.List;

public interface DigitalUserDataProvider {

    DigitalUser createDigitalUser(DigitalUserCreate digitalUserCreate);

    void deleteDigitalUser(String id);

    Asset createAsset(String digitalUserId, AssetCreate assetCreate);

    List<Asset> listAssets(
            Integer offset,
            Integer limit,
            String sourceSystem, // e.g. expense-tracker
            String type, // e.g. expense, expenseCategory
            String digitalUserId,
            LocalDate createdAtGte
    );

    void deleteAsset(String digitalUserId, String assetId);
}
