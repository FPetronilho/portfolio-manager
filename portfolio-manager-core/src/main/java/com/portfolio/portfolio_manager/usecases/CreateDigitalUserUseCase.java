package com.portfolio.portfolio_manager.usecases;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.portfolio.portfolio_manager.dataprovider.PortfolioManagerDataProvider;
import com.portfolio.portfolio_manager.domain.DigitalUser;
import com.portfolio.portfolio_manager.dto.DigitalUserCreate;
import lombok.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateDigitalUserUseCase {

    private final PortfolioManagerDataProvider dataProvider;

    public Output execute(Input input) {
        DigitalUserCreate digitalUserCreate = input.getDigitalUserCreate();
        DigitalUser digitalUser = dataProvider.createDigitalUser(digitalUserCreate);

        return Output.builder()
                .digitalUser(digitalUser)
                .build();
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    public static class Input {
        private DigitalUserCreate digitalUserCreate;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    public static class Output {
        private DigitalUser digitalUser;
    }
}
