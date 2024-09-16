package com.portfolio.portfolio_manager.usecases;

import com.portfolio.portfolio_manager.dataprovider.PortfolioManagerDataProvider;
import com.portfolio.portfolio_manager.domain.Asset;
import lombok.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ListAssetsUseCase {

    private final PortfolioManagerDataProvider dataProvider;

    public Output execute(Input input) {
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
        private String sourceSystem;
        private String type;
        private String digitalUserId;
        private LocalDate createdAtGte;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    public static class Output {
        private List<Asset> assets;
    }
}