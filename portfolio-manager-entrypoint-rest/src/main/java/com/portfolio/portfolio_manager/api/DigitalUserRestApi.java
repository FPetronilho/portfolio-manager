package com.portfolio.portfolio_manager.api;

import com.portfolio.portfolio_manager.domain.DigitalUser;
import com.portfolio.portfolio_manager.dto.DigitalUserCreate;
import com.portfolio.portfolio_manager.util.Constants;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/digitalUsers")
@Validated
public interface DigitalUserRestApi {

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<DigitalUser> create(@RequestBody @Valid DigitalUserCreate digitalUserCreate);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(
            @PathVariable @Pattern(regexp = Constants.ID_REGEX, message = Constants.ID_INVALID_MSG) String id
    );
}
