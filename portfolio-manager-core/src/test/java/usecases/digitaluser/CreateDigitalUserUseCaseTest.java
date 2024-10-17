package usecases.digitaluser;

import com.portfolio.portfoliomanager.dataprovider.PortfolioManagerDataProvider;
import com.portfolio.portfoliomanager.domain.DigitalUser;
import com.portfolio.portfoliomanager.dto.DigitalUserCreate;
import com.portfolio.portfoliomanager.exception.ResourceAlreadyExistsException;
import com.portfolio.portfoliomanager.usecases.digitaluser.CreateDigitalUserUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CreateDigitalUserUseCaseTest {

    @Mock
    private PortfolioManagerDataProvider dataProvider;

    @InjectMocks
    private CreateDigitalUserUseCase createDigitalUserUseCase;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecute_shouldReturnDigitalUserIfDigitalUserDoesNotYetExist() {
        // Given
        DigitalUserCreate digitalUserCreate = new DigitalUserCreate();
        digitalUserCreate.setIdPInfo(DigitalUserCreate.IdentityProviderInformation.builder()
                .subject("test-subject")
                .tenantId("test-tenant")
                .identityProvider(DigitalUserCreate.IdentityProviderInformation.IdentityProvider.GOOGLE_IDENTITY_PLATFORM)
                .build());

        DigitalUser expectedDigitalUser = new DigitalUser();

        CreateDigitalUserUseCase.Input input = CreateDigitalUserUseCase.Input.builder()
                .digitalUserCreate(digitalUserCreate)
                .build();

        when(dataProvider.digitalUserExistsBySubAndIdPAndTenantId(
                digitalUserCreate.getIdPInfo().getSubject(),
                digitalUserCreate.getIdPInfo().getIdentityProvider(),
                digitalUserCreate.getIdPInfo().getTenantId()
        )).thenReturn(false);

        when(dataProvider.createDigitalUser(digitalUserCreate)).thenReturn(expectedDigitalUser);

        // When
        CreateDigitalUserUseCase.Output result = createDigitalUserUseCase.execute(input);

        // Then
        assertNotNull(result);
        assertEquals(expectedDigitalUser, result.getDigitalUser());
        verify(dataProvider).digitalUserExistsBySubAndIdPAndTenantId(
                digitalUserCreate.getIdPInfo().getSubject(),
                digitalUserCreate.getIdPInfo().getIdentityProvider(),
                digitalUserCreate.getIdPInfo().getTenantId()
        );
        verify(dataProvider).createDigitalUser(digitalUserCreate);
    }

    @Test
    void testExecute_shouldThrowResourceAlreadyExistsExceptionIfDigitalUserAlreadyExists() {
        //Given
        DigitalUserCreate digitalUserCreate = new DigitalUserCreate();
        digitalUserCreate.setIdPInfo(DigitalUserCreate.IdentityProviderInformation.builder()
                .subject("test-subject")
                .tenantId("test-tenant")
                .identityProvider(DigitalUserCreate.IdentityProviderInformation.IdentityProvider.GOOGLE_IDENTITY_PLATFORM)
                .build());

        CreateDigitalUserUseCase.Input input = CreateDigitalUserUseCase.Input.builder()
                .digitalUserCreate(digitalUserCreate)
                .build();

        when(dataProvider.digitalUserExistsBySubAndIdPAndTenantId(
                digitalUserCreate.getIdPInfo().getSubject(),
                digitalUserCreate.getIdPInfo().getIdentityProvider(),
                digitalUserCreate.getIdPInfo().getTenantId()
        )).thenReturn(true);

        // When & Then
        assertThrows(ResourceAlreadyExistsException.class, () -> createDigitalUserUseCase.execute(input));
    }
}
