package com.portfolio.portfoliomanager.usecases;

import com.portfolio.portfoliomanager.dataprovider.PortfolioManagerDataProvider;
import com.portfolio.portfoliomanager.domain.Asset;
import com.portfolio.portfoliomanager.domain.DigitalUser;
import com.portfolio.portfoliomanager.exception.AuthenticationFailedException;
import com.portfolio.portfoliomanager.exception.AuthorizationFailedException;
import com.portfolio.portfoliomanager.security.context.DigitalUserSecurityContext;
import com.portfolio.portfoliomanager.util.SecurityUtil;
import lombok.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteAssetUseCase {

    private final PortfolioManagerDataProvider dataProvider;
    private final FindAssetByExternalIdUseCase findAssetByExternalIdUseCase;

    public void execute(Input input) {
        // 1. Check if the user inputted in query param is the same as in the JWT.
        DigitalUserSecurityContext digitalUserSecurityContext = SecurityUtil.getDigitalUserSecurityContext();
        if (!digitalUserSecurityContext.getId().equals(input.getDigitalUserId())) {
            throw new AuthenticationFailedException(
                    "Authentication Failed: User ID does not match ID from JWT."
            );
        }

        // 2. Check if the digital user is the owner of the asset
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

        // 3. Delete the asset if everything checks out
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
