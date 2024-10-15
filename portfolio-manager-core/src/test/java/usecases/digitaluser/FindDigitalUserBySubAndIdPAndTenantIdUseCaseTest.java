package usecases.digitaluser;

import com.portfolio.portfoliomanager.dataprovider.PortfolioManagerDataProvider;
import com.portfolio.portfoliomanager.domain.DigitalUser;
import com.portfolio.portfoliomanager.usecases.digitaluser.FindDigitalUserBySubAndIdPAndTenantIdUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FindDigitalUserBySubAndIdPAndTenantIdUseCaseTest {

    @Mock
    private PortfolioManagerDataProvider dataProvider;

    @InjectMocks
    private FindDigitalUserBySubAndIdPAndTenantIdUseCase findDigitalUserBySubAndIdPAndTenantIdUseCase;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecute_shouldReturnDigitalUser() {
        // Given
        String subject = "test-subject";
        String tenantId = "test-tenantId";
        DigitalUser.IdentityProviderInformation.IdentityProvider idP =
                DigitalUser.IdentityProviderInformation.IdentityProvider.GOOGLE_IDENTITY_PLATFORM;

        DigitalUser expectedDigitalUser = new DigitalUser();

        FindDigitalUserBySubAndIdPAndTenantIdUseCase.Input input = FindDigitalUserBySubAndIdPAndTenantIdUseCase.Input
                .builder()
                .sub(subject)
                .idP(idP)
                .tenantId(tenantId)
                .build();

        when(dataProvider.getDigitalUserBySubAndIdPAndTenant(subject, idP, tenantId)).thenReturn(expectedDigitalUser);

        // When
        FindDigitalUserBySubAndIdPAndTenantIdUseCase.Output result =
                findDigitalUserBySubAndIdPAndTenantIdUseCase.execute(input);

        // Then
        assertNotNull(result);
        assertEquals(expectedDigitalUser, result.getDigitalUser());
        verify(dataProvider).getDigitalUserBySubAndIdPAndTenant(subject, idP, tenantId);
    }

}
