package com.portfolio.portfoliomanager.controller;

import com.portfolio.portfoliomanager.api.AssetRestApi;
import com.portfolio.portfoliomanager.domain.Asset;
import com.portfolio.portfoliomanager.dto.AssetCreate;
import com.portfolio.portfoliomanager.usecases.CreateAssetUseCase;
import com.portfolio.portfoliomanager.usecases.DeleteAssetUseCase;
import com.portfolio.portfoliomanager.usecases.ListAssetsUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
public class AssetController implements AssetRestApi {

    private final CreateAssetUseCase createAssetUseCase;
    private final ListAssetsUseCase listAssetsUseCase;
    private final DeleteAssetUseCase deleteAssetUseCase;

    @Override
    public ResponseEntity<Asset> create(AssetCreate assetCreate, String digitalUserId) {
        log.info("Creating asset {} for user {}.", assetCreate, digitalUserId);
        CreateAssetUseCase.Input input = CreateAssetUseCase.Input.builder()
                .assetCreate(assetCreate)
                .digitalUserId(digitalUserId)
                .build();

        CreateAssetUseCase.Output output = createAssetUseCase.execute(input);
        return new ResponseEntity<>(output.getAsset(), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<Asset>> list(
            Integer offset,
            Integer limit,
            String digitalUserId,
            String ids,
            String groupId,
            String artifactId,
            String type,
            LocalDateTime createdAtLte,
            LocalDateTime createdAt,
            LocalDateTime createdAtGte
    ) {

        ListAssetsUseCase.Input input = ListAssetsUseCase.Input.builder()
                .offset(offset)
                .limit(limit)
                .digitalUserId(digitalUserId)
                .ids(ids)
                .groupId(groupId)
                .artifactId(artifactId)
                .type(type)
                .createdAtLte(createdAtLte)
                .createdAt(createdAt)
                .createdAtGte(createdAtGte)
                .build();

        log.info("Listing assets: {}.", input);
        ListAssetsUseCase.Output output = listAssetsUseCase.execute(input);
        return new ResponseEntity<>(output.getAssets(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> delete(String externalId) {
        log.info("Deleting asset {}.", externalId);
        DeleteAssetUseCase.Input input = DeleteAssetUseCase.Input.builder()
                .externalId(externalId)
                .build();

        deleteAssetUseCase.execute(input);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
