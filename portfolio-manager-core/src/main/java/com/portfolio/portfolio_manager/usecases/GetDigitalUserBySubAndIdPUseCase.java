package com.portfolio.portfolio_manager.usecases;

import com.portfolio.portfolio_manager.dataprovider.PortfolioManagerDataProvider;
import com.portfolio.portfolio_manager.domain.DigitalUser;
import lombok.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetDigitalUserBySubAndIdPUseCase {

    private final PortfolioManagerDataProvider dataProvider;

    public Output execute(Input input) {
        DigitalUser digitalUser = dataProvider.getDigitalUserBySubAndIdP(input.getSub(), input.getIdP());

        return Output.builder()
                .digitalUser(digitalUser)
                .build();
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    public static class Input {
        private String sub;
        private DigitalUser.IdentityProviderInformation.IdentityProvider idP;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    public static class Output {
        private DigitalUser digitalUser;
    }
}
