package com.portfolio.portfolio_manager.mapper;

import com.portfolio.portfolio_manager.document.AssetDocument;
import com.portfolio.portfolio_manager.document.DigitalUserDocument;
import com.portfolio.portfolio_manager.domain.Asset;
import com.portfolio.portfolio_manager.domain.DigitalUser;
import com.portfolio.portfolio_manager.dto.AssetCreate;
import com.portfolio.portfolio_manager.dto.DigitalUserCreate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;
import java.util.UUID;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        imports = {UUID.class}
)
public interface PortfolioManagerMapperDataProvider {

    DigitalUser toDigitalUser(DigitalUserDocument digitalUserDocument);

    List<DigitalUser> toDigitalUserList(List<DigitalUserDocument> digitalUserDocuments);

    @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID().toString())")
    @Mapping(target = "dbId", ignore = true)
    @Mapping(target = "assets", ignore = true)
    DigitalUserDocument toDigitalUserDocument(DigitalUserCreate digitalUserCreate);

    Asset toAsset(AssetDocument assetDocument);

    List<Asset> toAssetList(List<AssetDocument> assetDocuments);

    @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID().toString())")
    @Mapping(target = "dbId", ignore = true)
    AssetDocument toAssetDocument(AssetCreate assetCreate);
}
