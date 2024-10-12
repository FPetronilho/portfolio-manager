package usecases.asset;

import com.portfolio.portfoliomanager.dataprovider.PortfolioManagerDataProvider;
import com.portfolio.portfoliomanager.domain.Asset;
import com.portfolio.portfoliomanager.usecases.asset.FindAssetByExternalIdUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class FindAssetByExternalIdUseCaseTest {

    @Mock
    private PortfolioManagerDataProvider dataProvider;

    @InjectMocks
    private FindAssetByExternalIdUseCase findAssetByExternalIdUseCase;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecute_shouldReturnAsset() {
        // Given
        String digitalUserId = "test-digitalUserId";
        String externalId = "test-externalId";

        Asset expectedAsset = new Asset();

        FindAssetByExternalIdUseCase.Input input = FindAssetByExternalIdUseCase.Input.builder()
                .digitalUserId(digitalUserId)
                .externalId(externalId)
                .build();

        when(dataProvider.findAssetByExternalId(digitalUserId,externalId)).thenReturn(expectedAsset);

        // When
        FindAssetByExternalIdUseCase.Output result = findAssetByExternalIdUseCase.execute(input);

        // Then
        assertNotNull(result);
        assertEquals(expectedAsset, result.getAsset());
    }
}
