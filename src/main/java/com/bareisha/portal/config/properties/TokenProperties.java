package com.bareisha.portal.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("token")
public class TokenProperties {
    private String secretKey;
    private Long ttlPeriod;

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public Long getTtlPeriod() {
        return ttlPeriod;
    }

    public void setTtlPeriod(Long ttlPeriod) {
        this.ttlPeriod = ttlPeriod;
    }
}
