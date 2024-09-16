package com.portfolio.portfolio_manager.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.portfolio.portfolio_manager.domain.Asset;
import com.portfolio.portfolio_manager.domain.DigitalUser;
import com.portfolio.portfolio_manager.util.Constants;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DigitalUserCreate {

    @NotNull(message = Constants.USER_IDP_INFO_MANDATORY_MSG)
    private DigitalUser.IdentityProviderInformation idPInfo;

    @NotNull(message = Constants.USER_PERSONAL_INFO_MANDATORY_MSG)
    private DigitalUser.PersonalInformation personalInfo;

    @NotNull(message = Constants.USER_CONTACT_MEDIUM_MANDATORY_MSG)
    private List<DigitalUser.ContactMedium> contactMediumList;

    @NotNull(message = Constants.USER_ASSETS_MANDATORY_MSG)
    private List<Asset> assets;
}
