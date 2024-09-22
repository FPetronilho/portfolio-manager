package com.portfolio.portfoliomanager.usecases;

import com.portfolio.portfoliomanager.dataprovider.PortfolioManagerDataProvider;
import com.portfolio.portfoliomanager.domain.DigitalUser;
import com.portfolio.portfoliomanager.dto.DigitalUserCreate;
import lombok.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateDigitalUserUseCase {

    private final PortfolioManagerDataProvider dataProvider;

    public Output execute(Input input) {
        DigitalUserCreate digitalUserCreate = input.getDigitalUserCreate();

        // TODO: call get by sub and IdP and Tenant ID. If already exists, throw exception.

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
