package com.portfolio.portfoliomanager.dataprovider;

import com.mongodb.client.result.DeleteResult;
import com.portfolio.portfoliomanager.document.DigitalUserDocument;
import com.portfolio.portfoliomanager.domain.Asset;
import com.portfolio.portfoliomanager.domain.DigitalUser;
import com.portfolio.portfoliomanager.dto.AssetCreate;
import com.portfolio.portfoliomanager.dto.DigitalUserCreate;
import com.portfolio.portfoliomanager.exception.ResourceNotFoundException;
import com.portfolio.portfoliomanager.mapper.PortfolioManagerMapperDataProvider;
import com.portfolio.portfoliomanager.usecases.asset.ListAssetsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PortfolioManagerDataProviderNoSql implements PortfolioManagerDataProvider {

    private final MongoTemplate mongoTemplate;
    private final PortfolioManagerMapperDataProvider mapper;

    @Override
    public DigitalUser createDigitalUser(DigitalUserCreate digitalUserCreate) {
        DigitalUserDocument digitalUserDocument = mapper.toDigitalUserDocument(digitalUserCreate);
        digitalUserDocument = mongoTemplate.save(digitalUserDocument);
        return mapper.toDigitalUser(digitalUserDocument);
    }

    @Override
    public DigitalUser getDigitalUserBySubAndIdPAndTenant(
            String sub,
            DigitalUser.IdentityProviderInformation.IdentityProvider idP,
            String tenantId
    ) {

        Query query = new Query().addCriteria(Criteria.where("idPInfo.subject").is(sub)
                .and("idPInfo.identityProvider").is(idP)
                .and("idPInfo.tenantId").is(tenantId));

        DigitalUserDocument digitalUserDocument = mongoTemplate.findOne(query, DigitalUserDocument.class);
        digitalUserDocument = Optional.ofNullable(digitalUserDocument).orElseThrow(
                () -> new ResourceNotFoundException(
                        DigitalUserDocument.class,
                        "combination of subject " + sub + ", idP " + idP + " and tenant " + tenantId)
        );

        return mapper.toDigitalUser(digitalUserDocument);
    }

    @Override
    public void deleteDigitalUser(String digitalUserId) {
        Query query = new Query().addCriteria(Criteria.where("id").is(digitalUserId));
        DeleteResult deleteResult = mongoTemplate.remove(query, DigitalUserDocument.class);

        if (deleteResult.getDeletedCount() == 0) {
            throw new ResourceNotFoundException(DigitalUserDocument.class, digitalUserId);
        }
    }

    @Override
    public Asset createAsset(AssetCreate assetCreate, String digitalUserId) {
        Asset asset = mapper.toAsset(assetCreate);
        DigitalUserDocument digitalUserDocument = findDigitalUserDocumentById(digitalUserId);
        digitalUserDocument.getAssets().add(asset);
        mongoTemplate.save(digitalUserDocument);
        return asset;
    }

    @Override
    public List<Asset> listAssets(ListAssetsUseCase.Input input) {
        Query query = new Query();
        query.with(PageRequest.of(input.getOffset(), input.getLimit()));

        if (input.getDigitalUserId() != null && !input.getDigitalUserId().isEmpty()) {
            query.addCriteria(Criteria.where("id").is(input.getDigitalUserId()));
        }

        // Add criteria for matching assets
        Criteria assetCriteria = new Criteria();
        assetCriteria.and("assets.artifactInfo.groupId").is(input.getGroupId());
        assetCriteria.and("assets.artifactInfo.artifactId").is(input.getArtifactId());
        assetCriteria.and("assets.type").is(input.getType());

        if (!CollectionUtils.isEmpty(input.getExternalIds())) {
            assetCriteria.and("assets.externalId").in(input.getExternalIds());
        }

        if (input.getCreatedAtGte() != null) {
            assetCriteria.and("assets.createdAt").gte(input.getCreatedAtGte());
        }

        query.addCriteria(assetCriteria);
        List<DigitalUserDocument> digitalUserDocuments = mongoTemplate.find(query, DigitalUserDocument.class);

        return digitalUserDocuments.stream()
                .flatMap(user -> user.getAssets().stream()) // Flatten the assets from multiple users
                .filter(asset -> { // Apply filtering logic to ensure only matching assets are collected
                    boolean matches = true;
                    matches &= asset.getArtifactInfo().getGroupId().equals(input.getGroupId());
                    matches &= asset.getArtifactInfo().getArtifactId().equals(input.getArtifactId());
                    matches &= asset.getType().equals(input.getType());

                    if (!CollectionUtils.isEmpty(input.getExternalIds())) {
                        matches &= input.getExternalIds().contains(asset.getExternalId());
                    }

                    if (input.getCreatedAtGte() != null) {
                        matches &= !asset.getCreatedAt().isBefore(input.getCreatedAtGte());
                    }

                    return matches;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAsset(String digitalUserId, String assetExternalId) {
        Query query = new Query(Criteria.where("id").is(digitalUserId)
                .and("assets.externalId").is(assetExternalId));
        boolean assetExists = mongoTemplate.exists(query, DigitalUserDocument.class);

        if (!assetExists) {
            throw new ResourceNotFoundException(Asset.class, assetExternalId);
        }

        // Finds every user that has an asset with the externalId given and "pulls" it out of the assets list.
        Update update = new Update().pull("assets", Query.query(Criteria.where("externalId").is(assetExternalId)));
        mongoTemplate.updateMulti(query, update, DigitalUserDocument.class);
    }

    private DigitalUserDocument findDigitalUserDocumentById(String id) {
        Query query = new Query().addCriteria(Criteria.where("id").is(id));
        DigitalUserDocument digitalUserDocument = mongoTemplate.findOne(query, DigitalUserDocument.class);

        digitalUserDocument = Optional.ofNullable(digitalUserDocument).orElseThrow(
                () -> new ResourceNotFoundException(DigitalUserDocument.class, id)
        );

        return digitalUserDocument;
    }

    @Override
    public boolean digitalUserExistsBySubAndIdPAndTenantId(
            String sub,
            DigitalUserCreate.IdentityProviderInformation.IdentityProvider idP,
            String tenantId
    ) {
        Query query = new Query().addCriteria(Criteria.where("idPInfo.subject").is(sub)
                .and("idPInfo.identityProvider").is(idP)
                .and("idPInfo.tenantId").is(tenantId)
        );

        return mongoTemplate.exists(query, DigitalUserDocument.class);
    }

    @Override
    public boolean assetExistsByExternalId(String externalId) {
        Query query = new Query().addCriteria(Criteria.where("assets.externalId").is(externalId));
        return mongoTemplate.exists(query, DigitalUserDocument.class);
    }

    @Override
    public Asset findAssetByExternalId(String digitalUserId, String externalId) {
        Query query = new Query().addCriteria(Criteria.where("id").is(digitalUserId));
        DigitalUserDocument digitalUserDocument = mongoTemplate.findOne(query, DigitalUserDocument.class);

        digitalUserDocument = Optional.ofNullable(digitalUserDocument).orElseThrow(
                () -> new ResourceNotFoundException(DigitalUserDocument.class, digitalUserId)
        );

        List<Asset> assets = digitalUserDocument.getAssets();

        return assets.stream()
                .filter(asset1 -> asset1.getExternalId().equals(externalId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(Asset.class, externalId));
    }
}
