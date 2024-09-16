package com.portfolio.portfolio_manager.usecases;

import com.portfolio.portfolio_manager.dataprovider.PortfolioManagerDataProvider;
import com.portfolio.portfolio_manager.domain.Asset;
import com.portfolio.portfolio_manager.dto.AssetCreate;
import lombok.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateAssetUseCase {

    private final PortfolioManagerDataProvider dataProvider;

    public Output execute(Input input) {
        AssetCreate assetCreate = input.getAssetCreate();
        Asset asset = dataProvider.createAsset(input.getDigitalUserId(), assetCreate);

        return Output.builder()
                .asset(asset)
                .build();
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    public static class Input {
        private String digitalUserId;
        private AssetCreate assetCreate;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    public static class Output {
        private Asset asset;
    }
}