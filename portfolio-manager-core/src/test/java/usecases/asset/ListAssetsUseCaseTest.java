package usecases.asset;

import com.portfolio.portfoliomanager.dataprovider.PortfolioManagerDataProvider;
import com.portfolio.portfoliomanager.domain.Asset;
import com.portfolio.portfoliomanager.exception.AuthenticationFailedException;
import com.portfolio.portfoliomanager.security.context.DigitalUserSecurityContext;
import com.portfolio.portfoliomanager.usecases.asset.ListAssetsUseCase;
import com.portfolio.portfoliomanager.util.SecurityUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ListAssetsUseCaseTest {

    @Mock
    private PortfolioManagerDataProvider dataProvider;

    @InjectMocks
    private ListAssetsUseCase listAssetsUseCase;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecute_shouldReturnAssetListWhenUserIsValid() {
        // Given
        Integer offset = 0;
        Integer limit = 10;
        String digitalUserId = "test-digitalUserId";
        List<String> externalIds = new ArrayList<>(List.of("test-externalIds"));
        String groupId = "test-group";
        String artifactId = "test-artifact";
        String type = "test-type";

        Asset asset = new Asset();
        List<Asset> expectedAssets = new ArrayList<>(List.of(asset));

        DigitalUserSecurityContext mockContext = mock(DigitalUserSecurityContext.class);
        when(mockContext.getId()).thenReturn("test-digitalUserId");

        ListAssetsUseCase.Input input = ListAssetsUseCase.Input.builder()
                .offset(offset)
                .limit(limit)
                .digitalUserId(digitalUserId)
                .externalIds(externalIds)
                .groupId(groupId)
                .artifactId(artifactId)
                .type(type)
                .createdAtLte(null)
                .createdAt(null)
                .createdAtGte(null)
                .build();

        try (var mockedSecurityUtil = mockStatic(SecurityUtil.class)) {
            mockedSecurityUtil.when(SecurityUtil::getDigitalUserSecurityContext).thenReturn(mockContext);
            when(dataProvider.listAssets(input)).thenReturn(expectedAssets);

            // When
            ListAssetsUseCase.Output result = listAssetsUseCase.execute(input);

            // Then
            assertNotNull(result);
            assertEquals(expectedAssets, result.getAssets());
            verify(dataProvider).listAssets(input);
        }
    }

    @Test
    void testExecute_shouldReturnAuthenticationFailedExceptionWhenUserDoesNotMatchJwt() {
        // Given
        Integer offset = 0;
        Integer limit = 10;
        String digitalUserId = "test-digitalUserId";
        List<String> externalIds = new ArrayList<>(List.of("test-externalIds"));
        String groupId = "test-group";
        String artifactId = "test-artifact";
        String type = "test-type";

        DigitalUserSecurityContext mockContext = mock(DigitalUserSecurityContext.class);
        when(mockContext.getId()).thenReturn("anotherDigitalUserId");

        ListAssetsUseCase.Input input = ListAssetsUseCase.Input.builder()
                .offset(offset)
                .limit(limit)
                .digitalUserId(digitalUserId)
                .externalIds(externalIds)
                .groupId(groupId)
                .artifactId(artifactId)
                .type(type)
                .createdAtLte(null)
                .createdAt(null)
                .createdAtGte(null)
                .build();

        try (var mockedSecurityUtil = mockStatic(SecurityUtil.class)) {
            mockedSecurityUtil.when(SecurityUtil::getDigitalUserSecurityContext).thenReturn(mockContext);

            // When & Then
            assertThrows(AuthenticationFailedException.class, () -> listAssetsUseCase.execute(input));
        }
    }
}
