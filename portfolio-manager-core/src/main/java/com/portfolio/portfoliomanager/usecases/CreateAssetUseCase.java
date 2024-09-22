package com.portfolio.portfoliomanager.usecases;

import com.portfolio.portfoliomanager.dataprovider.PortfolioManagerDataProvider;
import com.portfolio.portfoliomanager.domain.Asset;
import com.portfolio.portfoliomanager.dto.AssetCreate;
import com.portfolio.portfoliomanager.exception.ResourceAlreadyExistsException;
import lombok.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateAssetUseCase {

    private final PortfolioManagerDataProvider dataProvider;

    public Output execute(Input input) {
        AssetCreate assetCreate = input.getAssetCreate();

        /* Guarantees that the asset does not yet exist.
        This prevents having multiple assets and more importantly, it guarantees that the same user cannot have an asset
        where he is an Owner and a Viewer at the same time.
         */
        if (dataProvider.assetExistsByExternalId(assetCreate.getExternalId())) {
            throw new ResourceAlreadyExistsException(Asset.class, assetCreate.getExternalId());
        }

        Asset asset = dataProvider.createAsset(assetCreate, input.getDigitalUserId());
        return Output.builder()
                .asset(asset)
                .build();
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    public static class Input {
        private AssetCreate assetCreate;
        private String digitalUserId;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    public static class Output {
        private Asset asset;
    }
}
