package com.tiago.money.money.config.profile;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("money")
public class MoneyProfile {

    private Seguranca seguranca = new Seguranca();
    private Email email = new Email();
    private String originPermission = "*";

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

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

    public static class Email {

        private String host;
        private int port;
        private String username;
        private String password;

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
