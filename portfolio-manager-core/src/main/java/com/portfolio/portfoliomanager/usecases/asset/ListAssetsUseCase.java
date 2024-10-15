package com.portfolio.portfoliomanager.usecases.asset;

import com.portfolio.portfoliomanager.dataprovider.PortfolioManagerDataProvider;
import com.portfolio.portfoliomanager.domain.Asset;
import com.portfolio.portfoliomanager.exception.AuthenticationFailedException;
import com.portfolio.portfoliomanager.security.context.DigitalUserSecurityContext;
import com.portfolio.portfoliomanager.util.SecurityUtil;
import lombok.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ListAssetsUseCase {

    private final PortfolioManagerDataProvider dataProvider;

    public Output execute(Input input) {
        // 1. Check if the user inputted in query param is the same as in the JWT.
        DigitalUserSecurityContext digitalUserSecurityContext = SecurityUtil.getDigitalUserSecurityContext();
        if (!digitalUserSecurityContext.getId().equals(input.getDigitalUserId())) {
            throw new AuthenticationFailedException(
                    "Authentication Failed: User ID does not match ID from JWT."
            );
        }

        // 2. If user checks out, proceed with listing the assets.
        List<Asset> assets = dataProvider.listAssets(input);

        return Output.builder()
                .assets(assets)
                .build();
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    public static class Input {

        private Integer offset;
        private Integer limit;
        private String digitalUserId;
        private List<String> externalIds;
        private String groupId;
        private String artifactId;
        private String type;
        private LocalDateTime createdAtLte;
        private LocalDateTime createdAt;
        private LocalDateTime createdAtGte;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    public static class Output {
        private List<Asset> assets;
    }
}
