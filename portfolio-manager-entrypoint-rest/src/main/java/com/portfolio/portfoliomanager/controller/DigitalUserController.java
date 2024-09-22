package com.portfolio.portfoliomanager.controller;

import com.portfolio.portfoliomanager.api.DigitalUserRestApi;
import com.portfolio.portfoliomanager.domain.DigitalUser;
import com.portfolio.portfoliomanager.dto.DigitalUserCreate;
import com.portfolio.portfoliomanager.usecases.CreateDigitalUserUseCase;
import com.portfolio.portfoliomanager.usecases.DeleteDigitalUserUseCase;
import com.portfolio.portfoliomanager.usecases.GetDigitalUserBySubAndIdPUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
public class DigitalUserController implements DigitalUserRestApi {

    private final CreateDigitalUserUseCase createDigitalUserUseCase;
    private final GetDigitalUserBySubAndIdPUseCase getDigitalUserBySubAndIdPUseCase;
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
    public ResponseEntity<DigitalUser> getDigitalUserBySubAndIdP(
            String sub,
            DigitalUser.IdentityProviderInformation.IdentityProvider idP
    ) {

        log.info("Getting digital user by subject: {} and identity provider: {}.", sub, idP);
        GetDigitalUserBySubAndIdPUseCase.Input input = GetDigitalUserBySubAndIdPUseCase.Input.builder()
                .sub(sub)
                .idP(idP)
                .build();

        GetDigitalUserBySubAndIdPUseCase.Output output = getDigitalUserBySubAndIdPUseCase.execute(input);
        return new ResponseEntity<>(output.getDigitalUser(), HttpStatus.OK);
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