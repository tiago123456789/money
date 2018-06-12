package com.tiago.money.money.config.profile;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("money")
public class MoneyProfile {

    private Seguranca seguranca;
    private String originPermission = "*";

    public String getOriginPermission() {
        return originPermission;
    }

    public void setOriginPermission(String originPermission) {
        this.originPermission = originPermission;
    }

    public Seguranca getSeguranca() {
        return seguranca;
    }

    public void setSeguranca(Seguranca seguranca) {
        this.seguranca = seguranca;
    }

    public static class Seguranca {

        private boolean enableHttps;

        public Boolean getEnableHttps() {
            return enableHttps;
        }

        public void setEnableHttps(Boolean enableHttps) {
            this.enableHttps = enableHttps;
        }

        public boolean isEnableHttps() {
            return enableHttps;
        }

        public void setEnableHttps(boolean enableHttps) {
            this.enableHttps = enableHttps;
        }
    }
}
