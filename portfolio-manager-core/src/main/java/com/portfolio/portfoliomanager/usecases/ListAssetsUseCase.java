package com.portfolio.portfoliomanager.usecases;

import com.portfolio.portfoliomanager.dataprovider.PortfolioManagerDataProvider;
import com.portfolio.portfoliomanager.domain.Asset;
import lombok.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        private String digitalUserId;
        private String ids;
        private String groupId;
        private String artifactId;
        private String type;
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
