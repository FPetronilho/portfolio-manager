package com.portfolio.portfolio_manager.usecases;

import com.portfolio.portfolio_manager.dataprovider.PortfolioManagerDataProvider;
import lombok.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteDigitalUserUseCase {

    private final PortfolioManagerDataProvider dataProvider;

    public void execute(Input input) {
        dataProvider.deleteDigitalUser(input.getId());
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    public static class Input {
        private String id;
    }
}
