package com.portfolio.portfolio_manager.controller;

import com.portfolio.portfolio_manager.api.AssetRestApi;
import com.portfolio.portfolio_manager.domain.Asset;
import com.portfolio.portfolio_manager.dto.AssetCreate;
import com.portfolio.portfolio_manager.usecases.CreateAssetUseCase;
import com.portfolio.portfolio_manager.usecases.DeleteAssetUseCase;
import com.portfolio.portfolio_manager.usecases.ListAssetsUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
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
            LocalDate createdAtGte
    ) {

        ListAssetsUseCase.Input input = ListAssetsUseCase.Input.builder()
                .offset(offset)
                .limit(limit)
                .digitalUserId(digitalUserId)
                .ids(ids)
                .groupId(groupId)
                .artifactId(artifactId)
                .type(type)
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
