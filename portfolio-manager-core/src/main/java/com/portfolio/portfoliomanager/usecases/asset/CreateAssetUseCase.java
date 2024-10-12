package com.portfolio.portfoliomanager.usecases.asset;

import com.portfolio.portfoliomanager.dataprovider.PortfolioManagerDataProvider;
import com.portfolio.portfoliomanager.domain.Asset;
import com.portfolio.portfoliomanager.dto.AssetCreate;
import com.portfolio.portfoliomanager.exception.AuthenticationFailedException;
import com.portfolio.portfoliomanager.exception.ResourceAlreadyExistsException;
import com.portfolio.portfoliomanager.security.context.DigitalUserSecurityContext;
import com.portfolio.portfoliomanager.util.SecurityUtil;
import lombok.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateAssetUseCase {

    private final PortfolioManagerDataProvider dataProvider;

    public Output execute(Input input) {
        // 1. Check if the user inputted in query param is the same as in the JWT.
        DigitalUserSecurityContext digitalUserSecurityContext = SecurityUtil.getDigitalUserSecurityContext();
        if (!digitalUserSecurityContext.getId().equals(input.getDigitalUserId())) {
            throw new AuthenticationFailedException(
                    "Authentication Failed: User ID does not match ID from JWT."
            );
        }

        /* 2. Guarantee that the asset does not yet exist.
        This prevents having multiple copies of the same asset and more importantly, it guarantees that the same user
        cannot have an asset where he is an Owner and a Viewer at the same time.
         */
        AssetCreate assetCreate = input.getAssetCreate();

        if (dataProvider.assetExistsByExternalId(assetCreate.getExternalId())) {
            throw new ResourceAlreadyExistsException(Asset.class, assetCreate.getExternalId());
        }

        // 3. If everything checks out, proceed with creating the asset
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
