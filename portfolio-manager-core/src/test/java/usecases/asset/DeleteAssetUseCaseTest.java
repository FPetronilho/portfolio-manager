package usecases.asset;

import com.portfolio.portfoliomanager.dataprovider.PortfolioManagerDataProvider;
import com.portfolio.portfoliomanager.domain.Asset;
import com.portfolio.portfoliomanager.exception.AuthenticationFailedException;
import com.portfolio.portfoliomanager.exception.AuthorizationFailedException;
import com.portfolio.portfoliomanager.security.context.DigitalUserSecurityContext;
import com.portfolio.portfoliomanager.usecases.asset.DeleteAssetUseCase;
import com.portfolio.portfoliomanager.usecases.asset.FindAssetByExternalIdUseCase;
import com.portfolio.portfoliomanager.util.SecurityUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DeleteAssetUseCaseTest {

    @Mock
    private PortfolioManagerDataProvider dataProvider;

    @Mock
    private FindAssetByExternalIdUseCase findAssetByExternalIdUseCase;

    @InjectMocks
    private DeleteAssetUseCase deleteAssetUseCase;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecute_shouldDeleteAssetWhenEverythingIsValid() {
        // Given
        String digitalUserId = "test-digitalUserId";
        String externalId = "test-externalId";

        Asset asset = new Asset();
        asset.setPermissionPolicy(Asset.PermissionPolicy.OWNER);

        DigitalUserSecurityContext mockContext = mock(DigitalUserSecurityContext.class);
        when(mockContext.getId()).thenReturn("test-digitalUserId");

        DeleteAssetUseCase.Input input = DeleteAssetUseCase.Input.builder()
                .digitalUserId(digitalUserId)
                .externalId(externalId)
                .build();

        FindAssetByExternalIdUseCase.Input findAssetInput = FindAssetByExternalIdUseCase.Input.builder()
                .externalId(externalId)
                .digitalUserId(digitalUserId)
                .build();

        FindAssetByExternalIdUseCase.Output findAssetOutput = FindAssetByExternalIdUseCase.Output.builder()
                .asset(asset)
                .build();

        try (var mockedSecurityUtil = mockStatic(SecurityUtil.class)) {
            mockedSecurityUtil.when(SecurityUtil::getDigitalUserSecurityContext).thenReturn(mockContext);
            when(findAssetByExternalIdUseCase.execute(findAssetInput)).thenReturn(findAssetOutput);

            // When
            deleteAssetUseCase.execute(input);

            // Then
            verify(dataProvider).deleteAsset(digitalUserId, externalId);
        }
    }

    @Test
    void testExecute_shouldThrowAuthenticationFailedExceptionWhenDigitalUserDoesNotMatchJwt() {
        // Given
        String digitalUserId = "test-digitalUserId";
        String externalId = "test-externalId";

        DigitalUserSecurityContext mockContext = mock(DigitalUserSecurityContext.class);
        when(mockContext.getId()).thenReturn("anotherDigitalUserId");

        DeleteAssetUseCase.Input input = DeleteAssetUseCase.Input.builder()
                .digitalUserId(digitalUserId)
                .externalId(externalId)
                .build();

        try (var mockedSecurityUtil = mockStatic(SecurityUtil.class)) {
            mockedSecurityUtil.when(SecurityUtil::getDigitalUserSecurityContext).thenReturn(mockContext);

            // When & Then
            assertThrows(AuthenticationFailedException.class, () -> deleteAssetUseCase.execute(input));
        }
    }

    @Test
    void testExecute_shouldThrowAuthorizationFailedExceptionWhenDigitalUserIsNotAssetOwner() {
        // Given
        String digitalUserId = "test-digitalUserId";
        String externalId = "test-externalId";

        Asset asset = new Asset();
        asset.setPermissionPolicy(Asset.PermissionPolicy.VIEWER);

        DigitalUserSecurityContext mockContext = mock(DigitalUserSecurityContext.class);
        when(mockContext.getId()).thenReturn("test-digitalUserId");

        DeleteAssetUseCase.Input input = DeleteAssetUseCase.Input.builder()
                .digitalUserId(digitalUserId)
                .externalId(externalId)
                .build();

        FindAssetByExternalIdUseCase.Output findAssetOutput = FindAssetByExternalIdUseCase.Output.builder()
                .asset(asset)
                .build();

        when(findAssetByExternalIdUseCase.execute(any())).thenReturn(findAssetOutput);

        try (var mockedSecurityUtil = mockStatic(SecurityUtil.class)) {
            mockedSecurityUtil.when(SecurityUtil::getDigitalUserSecurityContext).thenReturn(mockContext);

            // When & Then
            assertThrows(AuthorizationFailedException.class, () -> deleteAssetUseCase.execute(input));
        }
    }
}
