package com.portfolio.portfolio_manager.api;

import com.portfolio.portfolio_manager.domain.Asset;
import com.portfolio.portfolio_manager.dto.AssetCreate;
import com.portfolio.portfolio_manager.util.Constants;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequestMapping("/api/v1/assets")
@Validated
public interface AssetRestApi {

    @PostMapping(
            path = "/digitalUsers/{digitalUserId}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<Asset> create(
            @RequestBody @Valid AssetCreate assetCreate,

            @PathVariable @Pattern(regexp = Constants.ID_REGEX,
                    message = Constants.DIGITAL_USER_ID_INVALID_MSG) String digitalUserId
    );

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<Asset>> list(
            @RequestParam(required = false, defaultValue = Constants.DEFAULT_OFFSET)
                @Min(value = Constants.MIN_OFFSET, message = Constants.OFFSET_INVALID_MSG) Integer offset,

            @RequestParam(required = false, defaultValue = Constants.DEFAULT_LIMIT)
                @Min(value = Constants.MIN_LIMIT, message = Constants.LIMIT_INVALID_MSG)
                @Max(value = Constants.MAX_LIMIT, message = Constants.LIMIT_INVALID_MSG) Integer limit,

            @RequestParam
                @Pattern(regexp = Constants.ID_REGEX,
                        message = Constants.DIGITAL_USER_ID_INVALID_MSG) String digitalUserId,

            @RequestParam
                @Pattern(regexp = Constants.ID_LIST_REGEX,
                        message = Constants.ASSET_IDS_INVALID_MSG) String ids,

            //TODO: Change regex and messages for artifact
            @RequestParam(name = "artifactInfo.groupId")
                @Pattern(regexp = Constants.SOURCE_SYSTEM_REGEX,
                        message = Constants.SOURCE_SYSTEM_INVALID_MSG) String groupId,

            @RequestParam(name = "artifactInfo.artifactId")
            @Pattern(regexp = Constants.SOURCE_SYSTEM_REGEX,
                    message = Constants.SOURCE_SYSTEM_INVALID_MSG) String artifactId,

            @RequestParam
                @Pattern(regexp = Constants.TYPE_REGEX,
                        message = Constants.TYPE_INVALID_MSG) String type,

            @RequestParam(required = false)
                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate createdAtGte
            );

    @DeleteMapping("/{assetId}")
    ResponseEntity<Void> delete(
            @PathVariable @Pattern(regexp = Constants.ID_REGEX, message = Constants.ID_INVALID_MSG) String assetId
    );
}
