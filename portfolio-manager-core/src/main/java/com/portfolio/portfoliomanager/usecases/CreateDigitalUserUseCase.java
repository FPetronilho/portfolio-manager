package com.portfolio.portfoliomanager.usecases;

import com.portfolio.portfoliomanager.dataprovider.PortfolioManagerDataProvider;
import com.portfolio.portfoliomanager.domain.DigitalUser;
import com.portfolio.portfoliomanager.dto.DigitalUserCreate;
import com.portfolio.portfoliomanager.exception.ResourceAlreadyExistsException;
import lombok.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateDigitalUserUseCase {

    private final PortfolioManagerDataProvider dataProvider;

    public Output execute(Input input) {
        DigitalUserCreate digitalUserCreate = input.getDigitalUserCreate();

        // Check if digital user already exists.
        if (dataProvider.digitalUserExistsBySubAndIdPAndTenantId(
                digitalUserCreate.getIdPInfo().getSubject(),
                digitalUserCreate.getIdPInfo().getIdentityProvider(),
                digitalUserCreate.getIdPInfo().getTenantId())
        ){
            throw new ResourceAlreadyExistsException(DigitalUser.class,
                    "combination of subject " + digitalUserCreate.getIdPInfo().getSubject() +
                            ", idP " + digitalUserCreate.getIdPInfo().getIdentityProvider() +
                            " and tenant " + digitalUserCreate.getIdPInfo().getTenantId());
        }

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
