package com.tiago.money.money.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "money")
public class MoneyProperty {

    private final Seguranca seguranca = new Seguranca();
    private final Email email = new Email();
    private final S3 s3 = new S3();
    private String originPermission = "*";

    public Email getEmail() {
        return email;
    }

    public String getOriginPermission() {
        return originPermission;
    }

    public S3 getS3() {
        return this.s3;
    }

    public void setOriginPermission(String originPermission) {
        this.originPermission = originPermission;
    }

    public Seguranca getSeguranca() {
        return seguranca;
    }

    public static class S3 {

        private String clientId;
        private String clientSecret;
        private String bucketName = "tiago-dev-money";

        public String getBucketName() {
            return bucketName;
        }

        public void setBucketName(String bucketName) {
            this.bucketName = bucketName;
        }

        public String getClientId() {
            return clientId;
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

        public String getClientSecret() {
            return clientSecret;
        }

        public void setClientSecret(String clientSecret) {
            this.clientSecret = clientSecret;
        }
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
