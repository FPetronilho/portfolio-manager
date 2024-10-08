package com.portfolio.portfoliomanager.usecases;

import com.portfolio.portfoliomanager.dataprovider.PortfolioManagerDataProvider;
import com.portfolio.portfoliomanager.domain.Asset;
import com.portfolio.portfoliomanager.exception.AuthorizationFailedException;
import lombok.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteAssetUseCase {

    private final PortfolioManagerDataProvider dataProvider;
    private final FindAssetByExternalIdUseCase findAssetByExternalIdUseCase;

    public void execute(Input input) {
        Asset asset = findAssetByExternalIdUseCase.execute(
                FindAssetByExternalIdUseCase.Input.builder()
                        .digitalUserId(input.getDigitalUserId())
                        .externalId(input.getExternalId())
                        .build()
        ).getAsset();

        if (!asset.getPermissionPolicy().equals(Asset.PermissionPolicy.OWNER)) {
            throw new AuthorizationFailedException(
                    String.format(
                            "Digital user %s cannot delete expense %s because it is not its owner",
                            input.getDigitalUserId(),
                            input.getExternalId()
                    )
            );
        }

        dataProvider.deleteAsset(input.getDigitalUserId(), input.getExternalId());
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    public static class Input {
        private String digitalUserId;
        private String externalId;
    }
}
