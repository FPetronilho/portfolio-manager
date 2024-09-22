package com.portfolio.portfoliomanager.usecases;

import com.portfolio.portfoliomanager.dataprovider.PortfolioManagerDataProvider;
import lombok.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteAssetUseCase {

    private final PortfolioManagerDataProvider dataProvider;

    public void execute(Input input) {
        dataProvider.deleteAsset(input.getExternalId());
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    public static class Input {
        private String externalId;
    }
}
