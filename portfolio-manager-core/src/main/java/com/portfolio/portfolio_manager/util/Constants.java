package com.portfolio.portfolio_manager.util;

public class Constants {

    // Default values
    public static final String DEFAULT_OFFSET = "0";
    public static final String DEFAULT_LIMIT = "10";
    public static final int MIN_OFFSET = 0;
    public static final int MIN_LIMIT = 1;
    public static final int MAX_LIMIT = 100;


    // Database
    public static final String CREATED_AT_DB_FIELD = "createdAt";


    // Required fields validation
    public static final String USER_IDP_INFO_MANDATORY_MSG = "'idPInfo' is mandatory.";
    public static final String USER_PERSONAL_INFO_MANDATORY_MSG = "'personalInfo' is mandatory.";
    public static final String USER_CONTACT_MEDIUM_MANDATORY_MSG = "'contactMediumList' is mandatory.";
    public static final String USER_ASSETS_MANDATORY_MSG = "'assets' is mandatory.";
    public static final String ASSET_TYPE_MANDATORY_MSG = "'type' is mandatory.";
    public static final String ASSET_ARTIFACT_INFO_MANDATORY_MSG = "'artifactInfo' is mandatory.";
    public static final String ASSET_PERMISSION_POLICY_MANDATORY_MSG = "'permissionPolicy' is mandatory.";


    // Regex
    public static final String SOURCE_SYSTEM_REGEX = "[ \\wÀ-ú\\.:,;\\-\\[\\]()]{1,30}";
    public static final String Type_REGEX = "[ \\wÀ-ú\\.:,;\\-\\[\\]()]{1,30}";
    public static final String ID_REGEX = "[a-fA-F\\d\\-]{36}";


    // Fields validation
    public static final String ID_INVALID_MSG = "'id' must match: " + ID_REGEX + ".";
    public static final String OFFSET_INVALID_MSG = "'offset' must be positive";
    public static final String LIMIT_INVALID_MSG = "'limit' must be in the range [" + MIN_LIMIT + ", " + MAX_LIMIT + "]";
    public static final String SOURCE_SYSTEM_INVALID_MSG = "'sourceSystem' must match: " + SOURCE_SYSTEM_REGEX + ".";
    public static final String TYPE_INVALID_MSG = "'type' must match: " + Type_REGEX + ".";
    public static final String DIGITAL_USER_ID_INVALID_MSG = "'digitalUserId' must match: " + ID_REGEX + ".";
}
