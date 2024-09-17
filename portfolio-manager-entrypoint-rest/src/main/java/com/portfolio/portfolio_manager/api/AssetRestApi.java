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
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<Asset> create(
            @RequestBody @Valid AssetCreate assetCreate,

            @RequestParam(required = true)
                @Pattern(regexp = Constants.ID_REGEX,
                        message = Constants.DIGITAL_USER_ID_INVALID_MSG) String sub
    );

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<Asset>> list(
            @RequestParam(required = false, defaultValue = Constants.DEFAULT_OFFSET)
                @Min(value = Constants.MIN_OFFSET, message = Constants.OFFSET_INVALID_MSG) Integer offset,

            @RequestParam(required = false, defaultValue = Constants.DEFAULT_LIMIT)
                @Min(value = Constants.MIN_LIMIT, message = Constants.LIMIT_INVALID_MSG)
                @Max(value = Constants.MAX_LIMIT, message = Constants.LIMIT_INVALID_MSG) Integer limit,

            @RequestParam(required = true)
                @Pattern(regexp = Constants.ID_REGEX,
                        message = Constants.DIGITAL_USER_ID_INVALID_MSG) String sub,

            @RequestParam(required = true)
                @Pattern(regexp = Constants.SOURCE_SYSTEM_REGEX,
                        message = Constants.SOURCE_SYSTEM_INVALID_MSG) String sourceSystem,

            @RequestParam(required = true)
                @Pattern(regexp = Constants.Type_REGEX,
                        message = Constants.TYPE_INVALID_MSG) String type,

            @RequestParam(required = false)
                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate createdAtGte
            );

    @DeleteMapping("/{assetId}")
    ResponseEntity<Void> delete(
            @PathVariable @Pattern(regexp = Constants.ID_REGEX, message = Constants.ID_INVALID_MSG) String assetId,

            @RequestParam(required = true)
                @Pattern(regexp = Constants.ID_REGEX, message = Constants.ID_INVALID_MSG) String sub
    );
}
