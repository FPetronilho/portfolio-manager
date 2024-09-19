package com.portfolio.portfolio_manager.controller;

import com.portfolio.portfolio_manager.api.DigitalUserRestApi;
import com.portfolio.portfolio_manager.domain.DigitalUser;
import com.portfolio.portfolio_manager.dto.DigitalUserCreate;
import com.portfolio.portfolio_manager.usecases.CreateDigitalUserUseCase;
import com.portfolio.portfolio_manager.usecases.DeleteDigitalUserUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
public class DigitalUserController implements DigitalUserRestApi {

    private final CreateDigitalUserUseCase createDigitalUserUseCase;
    private final DeleteDigitalUserUseCase deleteDigitalUserUseCase;

    @Override
    public ResponseEntity<DigitalUser> create(DigitalUserCreate digitalUserCreate) {
        log.info("Creating digital user: {}.", digitalUserCreate);
        CreateDigitalUserUseCase.Input input = CreateDigitalUserUseCase.Input.builder()
                .digitalUserCreate(digitalUserCreate)
                .build();

        CreateDigitalUserUseCase.Output output = createDigitalUserUseCase.execute(input);
        return new ResponseEntity<>(output.getDigitalUser(), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<DigitalUser> getBySubAndIdP(
            String sub,
            DigitalUser.IdentityProviderInformation.IdentityProvider idP
    ) {

        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public ResponseEntity<Void> delete(String id) {
        log.info("Deleting digital user: {}.", id);
        DeleteDigitalUserUseCase.Input input = DeleteDigitalUserUseCase.Input.builder()
                .id(id)
                .build();

        deleteDigitalUserUseCase.execute(input);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
