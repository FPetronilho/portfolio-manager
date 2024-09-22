package com.portfolio.portfoliomanager.mapper;

import com.portfolio.portfoliomanager.document.DigitalUserDocument;
import com.portfolio.portfoliomanager.domain.Asset;
import com.portfolio.portfoliomanager.domain.DigitalUser;
import com.portfolio.portfoliomanager.dto.AssetCreate;
import com.portfolio.portfoliomanager.dto.DigitalUserCreate;
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

    @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID().toString())")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    Asset toAsset(AssetCreate assetCreate);
}
