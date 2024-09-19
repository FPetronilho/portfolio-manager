package com.portfolio.portfolio_manager.dataprovider;

import com.mongodb.client.result.DeleteResult;
import com.portfolio.portfolio_manager.document.DigitalUserDocument;
import com.portfolio.portfolio_manager.domain.Asset;
import com.portfolio.portfolio_manager.domain.DigitalUser;
import com.portfolio.portfolio_manager.dto.AssetCreate;
import com.portfolio.portfolio_manager.dto.DigitalUserCreate;
import com.portfolio.portfolio_manager.exception.ResourceAlreadyExistsException;
import com.portfolio.portfolio_manager.exception.ResourceNotFoundException;
import com.portfolio.portfolio_manager.mapper.PortfolioManagerMapperDataProvider;
import com.portfolio.portfolio_manager.usecases.ListAssetsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public DigitalUser getBySubAndIdP(String sub, DigitalUser.IdentityProviderInformation.IdentityProvider idP) {
        return null;
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

        /* Guarantees that the asset does not yet exist.
        This prevents having multiple assets and more importantly, it guarantees that the same user cannot have an asset
        where he is an Owner and a Viewer at the same time.
         */
        if (assetExistsByExternalId(assetCreate.getExternalId())) {
            throw new ResourceAlreadyExistsException(Asset.class, assetCreate.getExternalId());
        }

        DigitalUserDocument digitalUserDocument = findDigitalUserDocumentById(digitalUserId);
        digitalUserDocument.getAssets().add(asset);
        mongoTemplate.save(digitalUserDocument);
        return asset;
    }

    @Override
    public List<Asset> listAssets(ListAssetsUseCase.Input input) {
        return null;
    }

    @Override
    public void deleteAsset(String assetExternalId) {
        Query query = new Query(Criteria.where("assets.externalId").is(assetExternalId));

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

    private boolean assetExistsByExternalId(String externalId) {
        Query query = new Query().addCriteria(Criteria.where("assets.externalId").is(externalId));
        return mongoTemplate.exists(query, DigitalUserDocument.class);
    }
}
