package controller;

import com.portfolio.portfoliomanager.controller.DigitalUserController;
import com.portfolio.portfoliomanager.domain.DigitalUser;
import com.portfolio.portfoliomanager.dto.DigitalUserCreate;
import com.portfolio.portfoliomanager.usecases.CreateDigitalUserUseCase;
import com.portfolio.portfoliomanager.usecases.DeleteDigitalUserUseCase;
import com.portfolio.portfoliomanager.usecases.FindDigitalUserBySubAndIdPAndTenantIdUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DigitalUserControllerTest {

    @Mock
    private CreateDigitalUserUseCase createDigitalUserUseCase;

    @Mock
    private FindDigitalUserBySubAndIdPAndTenantIdUseCase findDigitalUserBySubAndIdPAndTenantIdUseCase;

    @Mock
    private DeleteDigitalUserUseCase deleteDigitalUserUseCase;

    @InjectMocks
    private DigitalUserController digitalUserController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate_shouldReturnHttpStatusCreatedAndDigitalUser() {
        // Given
        DigitalUserCreate digitalUserCreate = new DigitalUserCreate();
        DigitalUser expectedDigitalUser = new DigitalUser();

        CreateDigitalUserUseCase.Input input = CreateDigitalUserUseCase.Input.builder()
                .digitalUserCreate(digitalUserCreate)
                .build();

        CreateDigitalUserUseCase.Output output = CreateDigitalUserUseCase.Output.builder()
                .digitalUser(expectedDigitalUser)
                .build();

        when(createDigitalUserUseCase.execute(input)).thenReturn(output);

        // When
        ResponseEntity<DigitalUser> result = digitalUserController.create(digitalUserCreate);

        // Then
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(expectedDigitalUser, result.getBody());
        verify(createDigitalUserUseCase, times(1)).execute(any(CreateDigitalUserUseCase.Input.class));
    }

    @Test
    void testGetDigitalUserBySubAndIdPAndTenantId_shouldReturnHttpStatusOkAndDigitalUser() {
        // Given
        String sub = "test-sub";
        DigitalUser.IdentityProviderInformation.IdentityProvider idP =
                DigitalUser.IdentityProviderInformation.IdentityProvider.MICROSOFT_ENTRA_ID;
        String tenantId = "test-tenant";

        DigitalUser expectedDigitalUser = new DigitalUser();

        FindDigitalUserBySubAndIdPAndTenantIdUseCase.Input input = FindDigitalUserBySubAndIdPAndTenantIdUseCase.Input
                .builder()
                .sub(sub)
                .idP(idP)
                .tenantId(tenantId)
                .build();

        FindDigitalUserBySubAndIdPAndTenantIdUseCase.Output output = FindDigitalUserBySubAndIdPAndTenantIdUseCase.Output
                .builder()
                .digitalUser(expectedDigitalUser)
                .build();

        when(findDigitalUserBySubAndIdPAndTenantIdUseCase.execute(input)).thenReturn(output);

        // When
        ResponseEntity<DigitalUser> result = digitalUserController.getDigitalUserBySubAndIdPAndTenantId(sub, idP, tenantId);

        // Then
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(expectedDigitalUser, result.getBody());
        verify(findDigitalUserBySubAndIdPAndTenantIdUseCase, times(1))
                .execute(any(FindDigitalUserBySubAndIdPAndTenantIdUseCase.Input.class));
    }

    @Test
    void testDelete_shouldReturnHttpStatusNoContent(){
        // Given
        String id = "test-id";
        DeleteDigitalUserUseCase.Input input = DeleteDigitalUserUseCase.Input.builder()
                .id(id)
                .build();

        // When
        ResponseEntity<Void> result = digitalUserController.delete(id);

        // Then
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(deleteDigitalUserUseCase, times(1)).execute(any(DeleteDigitalUserUseCase.Input.class));
    }
}