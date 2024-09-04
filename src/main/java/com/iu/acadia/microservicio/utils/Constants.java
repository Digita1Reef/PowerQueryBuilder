package com.iu.acadia.microservicio.utils;

public class Constants {
    private Constants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String AD_LIMITS = "adLimits";
    public static final String AD_LIMITS_PER_APP = "adLimitsPerApp";

    public static final String CAMPAIGN_COLLECTION_NAME = "campaign";
    public static final String AUDIENCE_CLUSTER_COLLECTION_NAME = "audienceCluster";
    public static final String INSTALLED_APPS_COLLECTION_NAME = "installedApps";
    public static final String ORGANIZATION_COLLECTION_NAME =  "organization";
    public static final String CONFIGURATION_COLLECTION_NAME = "configurations";

    public static final String ATTRIBUTE_NOT_FOUND = "Error to read %s attribute : %s";
}
