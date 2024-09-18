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

    // Will be used by the Authorization Server (Auth8) to retrieve the digital user info + assets and issue the JWT
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<DigitalUser> getBySubAndIdP(
            @RequestParam(name = "idPInfo.subject")
                @Pattern(regexp = Constants.SUB_REGEX, message = Constants.SUB_INVALID_MSG) String sub,

            @RequestParam(name = "idPInfo.identityProvider") DigitalUser.IdentityProviderInformation.IdentityProvider idP
    );

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(
            @PathVariable @Pattern(regexp = Constants.ID_REGEX, message = Constants.ID_INVALID_MSG) String id
    );
}
