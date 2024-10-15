package com.portfolio.portfoliomanager.usecases.digitaluser;

import com.portfolio.portfoliomanager.dataprovider.PortfolioManagerDataProvider;
import com.portfolio.portfoliomanager.domain.DigitalUser;
import lombok.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindDigitalUserBySubAndIdPAndTenantIdUseCase {

    private final PortfolioManagerDataProvider dataProvider;

    public Output execute(Input input) {
        DigitalUser digitalUser = dataProvider.getDigitalUserBySubAndIdPAndTenant(
                input.getSub(),
                input.getIdP(),
                input.getTenantId()
        );

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
        private String tenantId;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    public static class Output {
        private DigitalUser digitalUser;
    }
}
