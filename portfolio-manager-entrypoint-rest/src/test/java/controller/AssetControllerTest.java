package controller;

import com.portfolio.portfoliomanager.controller.AssetController;
import com.portfolio.portfoliomanager.domain.Asset;
import com.portfolio.portfoliomanager.dto.AssetCreate;
import com.portfolio.portfoliomanager.usecases.asset.CreateAssetUseCase;
import com.portfolio.portfoliomanager.usecases.asset.DeleteAssetUseCase;
import com.portfolio.portfoliomanager.usecases.asset.ListAssetsUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AssetControllerTest {

    @Mock
    private CreateAssetUseCase createAssetUseCase;

    @Mock
    private ListAssetsUseCase listAssetsUseCase;

    @Mock
    private DeleteAssetUseCase deleteAssetUseCase;

    @InjectMocks
    private AssetController assetController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate_shouldReturnHttpStatusCreatedAndAsset() {
        // Given
        AssetCreate assetCreate = new AssetCreate();
        String digitalUserId = "test-id";

        Asset expectedAsset = new Asset();

        CreateAssetUseCase.Input input = CreateAssetUseCase.Input.builder()
                .assetCreate(assetCreate)
                .digitalUserId(digitalUserId)
                .build();

        CreateAssetUseCase.Output output = CreateAssetUseCase.Output.builder()
                .asset(expectedAsset)
                .build();

        when(createAssetUseCase.execute(input)).thenReturn(output);

        // When
        ResponseEntity<Asset> result = assetController.create(assetCreate, digitalUserId);

        // Then
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(expectedAsset, result.getBody());
        verify(createAssetUseCase, times(1)).execute(any(CreateAssetUseCase.Input.class));
    }

    @Test
    void testList_shouldReturnHttpStatusOkAndAssetList() {
        // Given
        Integer offset = 0;
        Integer limit = 10;
        String digitalUserId = "test-id";
        String externalIds = "test-externalIds";
        String groupId = "test-group";
        String artifactId = "test-artifact";
        String type = "test-type";

        Asset asset = new Asset();
        List<Asset> expectedAssets = new ArrayList<>(List.of(asset));

        ListAssetsUseCase.Input input = ListAssetsUseCase.Input.builder()
                .offset(offset)
                .limit(limit)
                .digitalUserId(digitalUserId)
                .externalIds(List.of(externalIds.split(",")))
                .groupId(groupId)
                .artifactId(artifactId)
                .type(type)
                .createdAtLte(null)
                .createdAt(null)
                .createdAtGte(null)
                .build();

        ListAssetsUseCase.Output output = ListAssetsUseCase.Output.builder()
                .assets(expectedAssets)
                .build();

        when(listAssetsUseCase.execute(input)).thenReturn(output);

        // When
        ResponseEntity<List<Asset>> results = assetController.list(
                offset,
                limit,
                digitalUserId,
                externalIds,
                groupId,
                artifactId,
                type,
                null,
                null,
                null
        );

        // Then
        assertEquals(HttpStatus.OK, results.getStatusCode());
        assertEquals(expectedAssets, results.getBody());
        verify(listAssetsUseCase, times(1)).execute(any(ListAssetsUseCase.Input.class));
    }

    @Test
    void testDelete_shouldReturnHttpStatusNoContent() {
        // Given
        String digitalUserId = "test-id";
        String externalId = "test-externalId";

        DeleteAssetUseCase.Input input = DeleteAssetUseCase.Input.builder()
                .digitalUserId(digitalUserId)
                .externalId(externalId)
                .build();

        // When
        ResponseEntity<Void> result = assetController.delete(digitalUserId, externalId);

        // Then
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(deleteAssetUseCase, times(1)).execute(any(DeleteAssetUseCase.Input.class));
    }
}
