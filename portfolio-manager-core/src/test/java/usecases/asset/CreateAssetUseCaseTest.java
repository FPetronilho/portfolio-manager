package usecases.asset;

import com.portfolio.portfoliomanager.dataprovider.PortfolioManagerDataProvider;
import com.portfolio.portfoliomanager.domain.Asset;
import com.portfolio.portfoliomanager.dto.AssetCreate;
import com.portfolio.portfoliomanager.exception.AuthenticationFailedException;
import com.portfolio.portfoliomanager.exception.ResourceAlreadyExistsException;
import com.portfolio.portfoliomanager.security.context.DigitalUserSecurityContext;
import com.portfolio.portfoliomanager.usecases.asset.CreateAssetUseCase;
import com.portfolio.portfoliomanager.util.SecurityUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CreateAssetUseCaseTest {

    @Mock
    private PortfolioManagerDataProvider dataProvider;

    @InjectMocks
    private CreateAssetUseCase createAssetUseCase;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecute_shouldReturnAssetWhenEverythingIsValid() {
        // Given
        AssetCreate assetCreate = new AssetCreate();
        assetCreate.setExternalId("test-externalId");
        String digitalUserId = "test-digitalUserId";

        Asset expectedAsset = new Asset();

        CreateAssetUseCase.Input input = CreateAssetUseCase.Input.builder()
                .assetCreate(assetCreate)
                .digitalUserId(digitalUserId)
                .build();

        DigitalUserSecurityContext mockContext = mock(DigitalUserSecurityContext.class);
        when(mockContext.getId()).thenReturn("test-digitalUserId");

        try(var mockedSecurityUtil = mockStatic(SecurityUtil.class)) {
            mockedSecurityUtil.when(SecurityUtil::getDigitalUserSecurityContext).thenReturn(mockContext);

            when(dataProvider.assetExistsByExternalId(assetCreate.getExternalId())).thenReturn(false);
            when(dataProvider.createAsset(assetCreate, digitalUserId)).thenReturn(expectedAsset);

            // When
            CreateAssetUseCase.Output result = createAssetUseCase.execute(input);

            // Then
            assertNotNull(result);
            assertEquals(result.getAsset(), expectedAsset);
        }
    }

    @Test
    void testExecute_shouldReturnResourceAlreadyExistsExceptionWhenAssetAlreadyExist() {
        // Given
        AssetCreate assetCreate = new AssetCreate();
        assetCreate.setExternalId("test-externalId");
        String digitalUserId = "test-digitalUserId";

        CreateAssetUseCase.Input input = CreateAssetUseCase.Input.builder()
                .assetCreate(assetCreate)
                .digitalUserId(digitalUserId)
                .build();

        DigitalUserSecurityContext mockContext = mock(DigitalUserSecurityContext.class);
        when(mockContext.getId()).thenReturn("test-digitalUserId");

        try (var mockedSecurityUtil = mockStatic(SecurityUtil.class)) {
            mockedSecurityUtil.when(SecurityUtil::getDigitalUserSecurityContext).thenReturn(mockContext);

            when(dataProvider.assetExistsByExternalId(assetCreate.getExternalId())).thenReturn(true);

            // When & Then
            assertThrows(ResourceAlreadyExistsException.class, () -> createAssetUseCase.execute(input));
        }
    }

    @Test
    void testExecute_shouldReturnAuthenticationFailedExceptionWhenUserDoesNotMatchJwtClaim() {
        // Given
        AssetCreate assetCreate = new AssetCreate();
        String digitalUserId = "test-digitalUserId";

        CreateAssetUseCase.Input input = CreateAssetUseCase.Input.builder()
                .digitalUserId(digitalUserId)
                .assetCreate(assetCreate)
                .build();

        DigitalUserSecurityContext mockContext = mock(DigitalUserSecurityContext.class);
        when(mockContext.getId()).thenReturn("anotherDigitalUserId");

        try (var mockedSecurityUtil = mockStatic(SecurityUtil.class)) {
            mockedSecurityUtil.when(SecurityUtil::getDigitalUserSecurityContext).thenReturn(mockContext);

            // When & Then
            assertThrows(AuthenticationFailedException.class, () -> createAssetUseCase.execute(input));
        }
    }
}
