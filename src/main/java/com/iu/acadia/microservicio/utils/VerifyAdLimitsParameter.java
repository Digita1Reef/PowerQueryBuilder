package com.iu.acadia.microservicio.utils;

import static com.iu.acadia.microservicio.utils.Constants.AD_LIMITS_PER_APP;
import static com.iu.acadia.microservicio.utils.Constants.AD_LIMITS;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;

import java.util.List;

@Slf4j
public class VerifyAdLimitsParameter {
    private final Document organization;
    private final List<String> targetPackages;
    private final Document adLimitPerApp;
    private final Document globalAdLimit;

    public VerifyAdLimitsParameter(Document organization, List<String> targetPackages) {
        this.organization = organization;
        this.targetPackages = targetPackages;
        this.adLimitPerApp = initAdLimitsPerApps();
        this.globalAdLimit = this.organization.get(AD_LIMITS, Document.class);
    }

    public boolean hasAdLimits() {
        return adLimitPerApp != null || globalAdLimit != null;
    }

    public boolean hasKey(String key) {
        return (this.adLimitPerApp != null && this.adLimitPerApp.containsKey(key)) || (this.globalAdLimit != null && this.globalAdLimit.containsKey(key));
    }

    private Document initAdLimitsPerApps() {
        try {
            // returns null if the source package is null or has more than one.
            if (targetPackages == null || targetPackages.size() != 1) {
                return null;
            }

            List<Document> perAppLimits = organization.getList(AD_LIMITS_PER_APP, Document.class);

            // find by source package
            for (Document doc : perAppLimits) {
                if (targetPackages.contains(doc.getString("sourcePackage"))) {
                    return doc.get(AD_LIMITS, Document.class);
                }
            }
        } catch (Exception e) {
            log.warn("Error init AdLimits per app : {}", e.getMessage());
        }
        return null;
    }

    public boolean getBooleanParameter(String key) {
        try {
            return this.adLimitPerApp == null ? this.globalAdLimit.getBoolean(key) : this.adLimitPerApp.getBoolean(key);
        } catch (Exception e) {
            log.warn("getBooleanParameter -> Error to read {} attribute : {}", key, e.getMessage());
        }
        return false;
    }

    public int getIntParameter(String key) {
        try {
            return this.adLimitPerApp == null ? this.globalAdLimit.getInteger(key) : this.adLimitPerApp.getInteger(key);
        } catch (Exception e) {
            log.warn("getIntParameter -> Error to read {} attribute : {}", key, e.getMessage());
        }
        return 0;
    }

    public String getStrParameter(String key) {
        try {
            return this.adLimitPerApp == null ? this.globalAdLimit.getString(key) : this.adLimitPerApp.getString(key);
        } catch (Exception e) {
            log.warn("getStrParameter -> Error to read {} attribute : {}", key, e.getMessage());
        }
        return "";
    }
}
