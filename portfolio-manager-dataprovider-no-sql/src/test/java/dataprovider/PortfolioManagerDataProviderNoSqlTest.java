package dataprovider;

import com.mongodb.client.result.DeleteResult;
import com.portfolio.portfoliomanager.dataprovider.PortfolioManagerDataProviderNoSql;
import com.portfolio.portfoliomanager.document.DigitalUserDocument;
import com.portfolio.portfoliomanager.domain.Asset;
import com.portfolio.portfoliomanager.domain.DigitalUser;
import com.portfolio.portfoliomanager.dto.AssetCreate;
import com.portfolio.portfoliomanager.dto.DigitalUserCreate;
import com.portfolio.portfoliomanager.exception.ResourceNotFoundException;
import com.portfolio.portfoliomanager.mapper.PortfolioManagerMapperDataProvider;
import com.portfolio.portfoliomanager.usecases.ListAssetsUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PortfolioManagerDataProviderNoSqlTest {

    @Mock
    private MongoTemplate mongoTemplate;

    @Mock
    private PortfolioManagerMapperDataProvider mapper;

    @InjectMocks
    private PortfolioManagerDataProviderNoSql portfolioManagerDataProviderNoSql;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateDigitalUser_shouldReturnCreatedDigitalUser() {
        // Given
        DigitalUserCreate digitalUserCreate = new DigitalUserCreate();
        DigitalUserDocument digitalUserDocument = new DigitalUserDocument();
        DigitalUser expectedDigitalUser = new DigitalUser();

        when(mapper.toDigitalUserDocument(digitalUserCreate)).thenReturn(digitalUserDocument);
        when(mongoTemplate.save(digitalUserDocument)).thenReturn(digitalUserDocument);
        when(mapper.toDigitalUser(digitalUserDocument)).thenReturn(expectedDigitalUser);

        // When
        DigitalUser result = portfolioManagerDataProviderNoSql.createDigitalUser(digitalUserCreate);

        // Then
        assertEquals(result, expectedDigitalUser);
        verify(mongoTemplate).save(digitalUserDocument);
        verify(mapper).toDigitalUserDocument(digitalUserCreate);
        verify(mapper).toDigitalUser(digitalUserDocument);
    }

    @Test
    void testGetDigitalUserBySubAndIdPAndTenant_shouldReturnDigitalUser() {
        // Given
        String sub = "test-sub";
        DigitalUser.IdentityProviderInformation.IdentityProvider idP =
                DigitalUser.IdentityProviderInformation.IdentityProvider.MICROSOFT_ENTRA_ID;
        String tenantId = "test-tenant";

        DigitalUserDocument digitalUserDocument = new DigitalUserDocument();
        DigitalUser expectedDigitalUser = new DigitalUser();

        when(mongoTemplate.findOne(any(Query.class), eq(DigitalUserDocument.class))).thenReturn(digitalUserDocument);
        when(mapper.toDigitalUser(digitalUserDocument)).thenReturn(expectedDigitalUser);

        // When
        DigitalUser result = portfolioManagerDataProviderNoSql.getDigitalUserBySubAndIdPAndTenant(sub, idP, tenantId);

        // Then
        assertEquals(result, expectedDigitalUser);
        verify(mongoTemplate).findOne(any(Query.class), eq(DigitalUserDocument.class));
        verify(mapper).toDigitalUser(digitalUserDocument);
    }

    @Test
    void testGetDigitalUserBySubAndIdPAndTenant_shouldThrowResourceNotFoundException_whenUserDoesNotExist() {
        // Given
        String sub = "test-sub";
        DigitalUser.IdentityProviderInformation.IdentityProvider idP =
                DigitalUser.IdentityProviderInformation.IdentityProvider.MICROSOFT_ENTRA_ID;
        String tenantId = "test-tenant";

        when(mongoTemplate.findOne(any(Query.class), eq(DigitalUserDocument.class))).thenReturn(null);

        // When & Then
        assertThrows(
                ResourceNotFoundException.class,
                () -> portfolioManagerDataProviderNoSql.getDigitalUserBySubAndIdPAndTenant(sub, idP, tenantId)
        );
    }

    @Test
    void testDeleteDigitalUser_shouldRemoveUser() {
        // Given
        String digitalUserId = "test-user";
        DeleteResult result = mock(DeleteResult.class);

        when(mongoTemplate.remove(any(Query.class), eq(DigitalUserDocument.class))).thenReturn(result);
        when(result.getDeletedCount()).thenReturn(1L);

        // When
        portfolioManagerDataProviderNoSql.deleteDigitalUser(digitalUserId);

        // Then
        verify(mongoTemplate).remove(any(Query.class), eq(DigitalUserDocument.class));
    }

    @Test
    void testDeleteDigitalUser_shouldThrowResourceNotFoundException_whenUserDoesNotExist() {
        // Given
        String digitalUserId = "test-user";
        DeleteResult result = mock(DeleteResult.class);

        when(mongoTemplate.remove(any(Query.class), eq(DigitalUserDocument.class))).thenReturn(result);
        when(result.getDeletedCount()).thenReturn(0L);

        // When & Then
        assertThrows(
                ResourceNotFoundException.class,
                () -> portfolioManagerDataProviderNoSql.deleteDigitalUser(digitalUserId)
        );
    }

    @Test
    void testCreateAsset_shouldAddAssetToUserAndReturnIt() {
        // Given
        AssetCreate assetCreate = new AssetCreate();
        String digitalUserId = "test-user";

        Asset expectedAsset = new Asset();
        DigitalUserDocument digitalUserDocument = new DigitalUserDocument();
        digitalUserDocument.setAssets(new ArrayList<>());

        when(mapper.toAsset(assetCreate)).thenReturn(expectedAsset);
        when(mongoTemplate.findOne(any(Query.class), eq(DigitalUserDocument.class))).thenReturn(digitalUserDocument);

        // When
        Asset result = portfolioManagerDataProviderNoSql.createAsset(assetCreate, digitalUserId);

        // Then
        assertEquals(result, expectedAsset);
        assertTrue(digitalUserDocument.getAssets().contains(expectedAsset));
        verify(mapper).toAsset(assetCreate);
        verify(mongoTemplate).save(digitalUserDocument);
    }

    @Test
    void testListAssets_shouldReturnedFilteredAssets() {
        // Given
        ListAssetsUseCase.Input input = new ListAssetsUseCase.Input();
        input.setOffset(0);
        input.setLimit(10);
        input.setGroupId("test-group");
        input.setArtifactId("test-artifact");
        input.setType("test-type");

        DigitalUserDocument digitalUserDocument = new DigitalUserDocument();
        Asset matchingAsset = new Asset();
        matchingAsset.setType("test-type");
        matchingAsset.setArtifactInfo(
                Asset.ArtifactInformation.builder()
                        .groupId("test-group")
                        .artifactId("test-artifact")
                        .version(null)
                        .build()
        );

        Asset nonMatchingAsset = new Asset();
        nonMatchingAsset.setType("other-type");
        nonMatchingAsset.setArtifactInfo(
                Asset.ArtifactInformation.builder()
                        .groupId("test-group")
                        .artifactId("test-artifact")
                        .version(null)
                        .build()
        );

        digitalUserDocument.setAssets(List.of(matchingAsset, nonMatchingAsset));

        when(mongoTemplate.find(any(Query.class), eq(DigitalUserDocument.class)))
                .thenReturn(List.of(digitalUserDocument));

        // When
        List<Asset> result = portfolioManagerDataProviderNoSql.listAssets(input);

        // Then
        assertEquals(1, result.size());
        assertTrue(result.contains(matchingAsset));
        assertFalse(result.contains(nonMatchingAsset));
    }

    @Test
    void testDeleteAsset_shouldRemoveAssetFromUser() {
        // Given
        String digitalUserId = "test-user";
        String externalUserId = "test-externalId";

        DigitalUserDocument digitalUserDocument = new DigitalUserDocument();
        Asset asset = new Asset();
        asset.setExternalId(externalUserId);
        digitalUserDocument.setAssets(new ArrayList<>(List.of(asset)));

        when(mongoTemplate.exists(any(Query.class), eq(DigitalUserDocument.class))).thenReturn(true);
        when(mongoTemplate.findOne(any(Query.class), eq(DigitalUserDocument.class))).thenReturn(digitalUserDocument);

        // When
        portfolioManagerDataProviderNoSql.deleteAsset(digitalUserId, externalUserId);

        // Then
        verify(mongoTemplate).updateMulti(any(Query.class), any(Update.class), eq(DigitalUserDocument.class));
    }

    @Test
    void testDeleteAsset_shouldThrowResourceNotFoundException_whenAssetDoesNotExist() {
        // Given
        String digitalUserId = "test-user";
        String externalUserId = "test-externalId";

        when(mongoTemplate.exists(any(Query.class), eq(DigitalUserDocument.class))).thenReturn(false);

        // When & Then
        assertThrows(
                ResourceNotFoundException.class,
                () -> portfolioManagerDataProviderNoSql.deleteAsset(digitalUserId, externalUserId)
        );
    }

    @Test
    void testDigitalUserExistsBySubAndIdPAndTenant_shouldReturnTrueWhenUserExists() {
        // Given
        String sub = "test-sub";
        DigitalUserCreate.IdentityProviderInformation.IdentityProvider idP =
                DigitalUserCreate.IdentityProviderInformation.IdentityProvider.MICROSOFT_ENTRA_ID;
        String tenantId = "test-tenant";

        when(mongoTemplate.exists(any(Query.class), eq(DigitalUserDocument.class))).thenReturn(true);

        // When
        boolean result = portfolioManagerDataProviderNoSql.digitalUserExistsBySubAndIdPAndTenantId(sub, idP, tenantId);

        // Then
        assertTrue(result);
        verify(mongoTemplate).exists(any(Query.class), eq(DigitalUserDocument.class));
    }

    @Test
    void testDigitalUserExistsBySubAndIdPAndTenant_shouldReturnFalseWhenUserDoesNotExist() {
        // Given
        String sub = "test-sub";
        DigitalUserCreate.IdentityProviderInformation.IdentityProvider idP =
                DigitalUserCreate.IdentityProviderInformation.IdentityProvider.MICROSOFT_ENTRA_ID;
        String tenantId = "test-tenant";

        when(mongoTemplate.exists(any(Query.class), eq(DigitalUserDocument.class))).thenReturn(false);

        // When
        boolean result = portfolioManagerDataProviderNoSql.digitalUserExistsBySubAndIdPAndTenantId(sub, idP, tenantId);

        // Then
        assertFalse(result);
        verify(mongoTemplate).exists(any(Query.class), eq(DigitalUserDocument.class));
    }

    @Test
    void testAssetExistsByExternalId_shouldReturnTrueWhenAssetExists() {
        // Given
        String externalId = "test-externalId";

        when(mongoTemplate.exists(any(Query.class), eq(DigitalUserDocument.class))).thenReturn(true);

        // When
        boolean result = portfolioManagerDataProviderNoSql.assetExistsByExternalId(externalId);

        // Then
        assertTrue(result);
        verify(mongoTemplate).exists(any(Query.class), eq(DigitalUserDocument.class));
    }

    @Test
    void testAssetExistsByExternalId_shouldReturnFalseWhenAssetDoesNotExist() {
        // Given
        String externalId = "test-externalId";

        when(mongoTemplate.exists(any(Query.class), eq(DigitalUserDocument.class))).thenReturn(false);

        // When
        boolean result = portfolioManagerDataProviderNoSql.assetExistsByExternalId(externalId);

        // Then
        assertFalse(result);
        verify(mongoTemplate).exists(any(Query.class), eq(DigitalUserDocument.class));
    }

    @Test
    void testFindAssetByExternalId_shouldReturnAsset() {
        // Given
        String digitalUserId = "test-user";
        String externalId = "test-externalId";

        DigitalUserDocument digitalUserDocument = new DigitalUserDocument();
        Asset expectedAsset = new Asset();
        expectedAsset.setExternalId(externalId);
        digitalUserDocument.setAssets(new ArrayList<>(List.of(expectedAsset)));

        when(mongoTemplate.findOne(any(Query.class), eq(DigitalUserDocument.class))).thenReturn(digitalUserDocument);

        // When
        Asset result = portfolioManagerDataProviderNoSql.findAssetByExternalId(digitalUserId, externalId);

        // Then
        assertEquals(result, expectedAsset);
        verify(mongoTemplate).findOne(any(Query.class), eq(DigitalUserDocument.class));
    }

    @Test
    void testFindAssetByExternalId_shouldThrowResourceNotFoundException_whenUserDoesNotExist() {
        // Given
        String digitalUserId = "test-user";
        String externalId = "test-externalId";

        when(mongoTemplate.findOne(any(Query.class), eq(DigitalUserDocument.class))).thenReturn(null);

        // When & Then
        assertThrows(
                ResourceNotFoundException.class,
                () -> portfolioManagerDataProviderNoSql.findAssetByExternalId(digitalUserId, externalId)
        );
    }

    @Test
    void testFindAssetByExternalId_shouldThrowResourceNotFoundException_whenAssetDoesNotExist() {
        // Given
        String digitalUserId = "test-user";
        String externalId = "test-externalId";

        DigitalUserDocument digitalUserDocument = new DigitalUserDocument();
        Asset asset = new Asset();
        asset.setExternalId("another-id");
        digitalUserDocument.setAssets(new ArrayList<>(List.of(asset)));

        when(mongoTemplate.findOne(any(Query.class), eq(DigitalUserDocument.class))).thenReturn(digitalUserDocument);

        // When & Then
        assertThrows(
                ResourceNotFoundException.class,
                () -> portfolioManagerDataProviderNoSql.findAssetByExternalId(digitalUserId, externalId)
        );
    }
}
