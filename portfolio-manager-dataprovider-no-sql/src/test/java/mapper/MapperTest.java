package mapper;

import com.portfolio.portfoliomanager.document.DigitalUserDocument;
import com.portfolio.portfoliomanager.domain.Asset;
import com.portfolio.portfoliomanager.domain.DigitalUser;
import com.portfolio.portfoliomanager.dto.AssetCreate;
import com.portfolio.portfoliomanager.dto.DigitalUserCreate;
import com.portfolio.portfoliomanager.mapper.PortfolioManagerMapperDataProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import util.TestDataUtil;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.List;

public class MapperTest {

    private PortfolioManagerMapperDataProvider mapper;

    @BeforeEach
    void setup() {
        mapper = Mappers.getMapper(PortfolioManagerMapperDataProvider.class);
    }

    @Test
    void testToDigitalUser() {
        // Arrange
        DigitalUserDocument digitalUserDocument = TestDataUtil.createTestDigitalUserDocumentA();

        // Act
        DigitalUser digitalUser = mapper.toDigitalUser(digitalUserDocument);

        // Assert
        assertNotNull(digitalUser);
        assertEquals("Test123", digitalUser.getId());
        assertEquals(Collections.emptyList(), digitalUser.getAssets());
        assertEquals(Collections.emptyList(), digitalUser.getContactMediumList());
        assertEquals("John Doe", digitalUser.getPersonalInfo().getNickName());
        assertEquals("Test Subject A", digitalUser.getIdPInfo().getSubject());
        assertEquals("Test Tenant A", digitalUser.getIdPInfo().getTenantId());
        assertEquals(
                DigitalUser.IdentityProviderInformation.IdentityProvider.MICROSOFT_ENTRA_ID,
                digitalUser.getIdPInfo().getIdentityProvider()
        );
    }

    @Test
    void testToDigitalUserList() {
        // Arrange
        DigitalUserDocument digitalUserDocumentA = TestDataUtil.createTestDigitalUserDocumentA();
        DigitalUserDocument digitalUserDocumentB = TestDataUtil.createTestDigitalUserDocumentB();
        List<DigitalUserDocument> digitalUserDocuments = List.of(digitalUserDocumentA, digitalUserDocumentB);

        // Act
        List<DigitalUser> digitalUsers = mapper.toDigitalUserList(digitalUserDocuments);

        // Assert
        assertNotNull(digitalUsers);
        assertEquals("Test123", digitalUsers.get(0).getId());
        assertEquals(Collections.emptyList(), digitalUsers.get(0).getAssets());
        assertEquals(Collections.emptyList(), digitalUsers.get(0).getContactMediumList());
        assertEquals("John Doe", digitalUsers.get(0).getPersonalInfo().getNickName());
        assertEquals("Test Subject A", digitalUsers.get(0).getIdPInfo().getSubject());
        assertEquals("Test Tenant A", digitalUsers.get(0).getIdPInfo().getTenantId());
        assertEquals(
                DigitalUser.IdentityProviderInformation.IdentityProvider.MICROSOFT_ENTRA_ID,
                digitalUsers.get(0).getIdPInfo().getIdentityProvider()
        );

        assertNotNull(digitalUsers);
        assertEquals("Test456", digitalUsers.get(1).getId());
        assertEquals(Collections.emptyList(), digitalUsers.get(1).getAssets());
        assertEquals(Collections.emptyList(), digitalUsers.get(1).getContactMediumList());
        assertEquals("Jane", digitalUsers.get(1).getPersonalInfo().getFirstName());
        assertEquals("Doe", digitalUsers.get(1).getPersonalInfo().getFamilyName());
        assertEquals("Test Subject B", digitalUsers.get(1).getIdPInfo().getSubject());
        assertEquals("Test Tenant B", digitalUsers.get(1).getIdPInfo().getTenantId());
        assertEquals(
                DigitalUser.IdentityProviderInformation.IdentityProvider.GOOGLE_IDENTITY_PLATFORM,
                digitalUsers.get(1).getIdPInfo().getIdentityProvider()
        );
    }

    @Test
    void testToDigitalUserDocument() {
        // Arrange
        DigitalUserCreate digitalUserCreate = TestDataUtil.createTestDigitalUserCreate();

        // Act
        DigitalUserDocument digitalUserDocument = mapper.toDigitalUserDocument(digitalUserCreate);

        // Assert
        assertNotNull(digitalUserDocument);
        assertNotNull(digitalUserDocument.getId());
        assertNull(digitalUserDocument.getAssets());
        assertEquals(Collections.emptyList(), digitalUserDocument.getContactMediumList());
        assertEquals("John Doe", digitalUserDocument.getPersonalInfo().getNickName());
        assertEquals("Test Subject A", digitalUserDocument.getIdPInfo().getSubject());
        assertEquals("Test Tenant A", digitalUserDocument.getIdPInfo().getTenantId());
        assertEquals(
                DigitalUser.IdentityProviderInformation.IdentityProvider.MICROSOFT_ENTRA_ID,
                digitalUserDocument.getIdPInfo().getIdentityProvider()
        );
    }

    @Test
    void testToAsset() {
        // Arrange
        AssetCreate assetCreate = TestDataUtil.createTestAssetCreate();

        // Act
        Asset asset = mapper.toAsset(assetCreate);

        // Assert
        assertNotNull(asset);
        assertEquals("Test ID", asset.getExternalId());
        assertEquals("Test Type", asset.getType());
        assertEquals("Test Group", asset.getArtifactInfo().getGroupId());
        assertEquals("Test Artifact", asset.getArtifactInfo().getArtifactId());
        assertEquals("Test Version", asset.getArtifactInfo().getVersion());
        assertEquals(Asset.PermissionPolicy.OWNER, asset.getPermissionPolicy());
    }
}
