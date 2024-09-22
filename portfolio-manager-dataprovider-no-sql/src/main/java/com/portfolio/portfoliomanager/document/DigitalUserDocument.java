package com.portfolio.portfoliomanager.document;

import com.portfolio.portfoliomanager.domain.Asset;
import com.portfolio.portfoliomanager.domain.DigitalUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
@Document(collection = "digital-user")
public class DigitalUserDocument extends BaseDocument {

    @Indexed(unique = true)
    private String id;

    private DigitalUser.IdentityProviderInformation idPInfo;
    private DigitalUser.PersonalInformation personalInfo;
    private List<DigitalUser.ContactMedium> contactMediumList;

    @Field("assets")
    private List<Asset> assets = new ArrayList<>();
}
