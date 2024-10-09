package util;

import com.portfolio.portfoliomanager.document.DigitalUserDocument;
import com.portfolio.portfoliomanager.domain.DigitalUser;
import com.portfolio.portfoliomanager.dto.AssetCreate;
import com.portfolio.portfoliomanager.dto.DigitalUserCreate;

import java.util.Collections;

public class TestDataUtil {

    public TestDataUtil() {
        throw new IllegalStateException("Cannot instantiate an util class.");
    }

    public static DigitalUserDocument createTestDigitalUserDocumentA() {
        DigitalUserDocument digitalUserDocument = new DigitalUserDocument();
        
        digitalUserDocument.setId("Test123");
        digitalUserDocument.setAssets(Collections.emptyList());
        digitalUserDocument.setIdPInfo(
                DigitalUser.IdentityProviderInformation.builder()
                        .subject("Test Subject A")
                        .tenantId("Test Tenant A")
                        .identityProvider(DigitalUser.IdentityProviderInformation.IdentityProvider.MICROSOFT_ENTRA_ID)
                        .build()
        );
        digitalUserDocument.setPersonalInfo(
                DigitalUser.PersonalInformation.builder()
                        .nickName("John Doe")
                        .build()
        );
        digitalUserDocument.setContactMediumList(Collections.emptyList());
        
        return digitalUserDocument;
    }

    public static DigitalUserDocument createTestDigitalUserDocumentB() {
        DigitalUserDocument digitalUserDocument = new DigitalUserDocument();

        digitalUserDocument.setId("Test456");
        digitalUserDocument.setAssets(Collections.emptyList());
        digitalUserDocument.setIdPInfo(
                DigitalUser.IdentityProviderInformation.builder()
                        .subject("Test Subject B")
                        .tenantId("Test Tenant B")
                        .identityProvider(DigitalUser.IdentityProviderInformation.IdentityProvider.GOOGLE_IDENTITY_PLATFORM)
                        .build()
        );
        digitalUserDocument.setPersonalInfo(
                DigitalUser.PersonalInformation.builder()
                        .firstName("Jane")
                        .familyName("Doe")
                        .build()
        );
        digitalUserDocument.setContactMediumList(Collections.emptyList());

        return digitalUserDocument;
    }

    public static DigitalUserCreate createTestDigitalUserCreate() {
        DigitalUserCreate digitalUserCreate = new DigitalUserCreate();

        digitalUserCreate.setIdPInfo(
                DigitalUserCreate.IdentityProviderInformation.builder()
                        .subject("Test Subject A")
                        .tenantId("Test Tenant A")
                        .identityProvider(DigitalUserCreate.IdentityProviderInformation.IdentityProvider.MICROSOFT_ENTRA_ID)
                        .build()
        );
        digitalUserCreate.setPersonalInfo(
                DigitalUserCreate.PersonalInformation.builder()
                        .nickName("John Doe")
                        .build()
        );
        digitalUserCreate.setContactMediumList(Collections.emptyList());

        return digitalUserCreate;
    }

    public static AssetCreate createTestAssetCreate() {
        return AssetCreate.builder()
                .type("Test Type")
                .externalId("Test ID")
                .permissionPolicy(AssetCreate.PermissionPolicy.OWNER)
                .artifactInfo(
                        AssetCreate.ArtifactInformation.builder()
                                .groupId("Test Group")
                                .artifactId("Test Artifact")
                                .version("Test Version")
                                .build()
                )
                .build();
    }
}
