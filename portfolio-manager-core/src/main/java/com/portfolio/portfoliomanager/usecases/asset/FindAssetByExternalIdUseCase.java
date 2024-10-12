package com.portfolio.portfoliomanager.usecases.asset;

import com.portfolio.portfoliomanager.dataprovider.PortfolioManagerDataProvider;
import com.portfolio.portfoliomanager.domain.Asset;
import lombok.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindAssetByExternalIdUseCase {

    private final PortfolioManagerDataProvider dataProvider;

    public Output execute(Input input) {
        Asset asset = dataProvider.findAssetByExternalId(
                input.getDigitalUserId(),
                input.getExternalId()
        );

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
        private String externalId;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    public static class Output {
        private Asset asset;
    }
}
