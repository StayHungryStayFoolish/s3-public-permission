package com.example.s3publicpermission.config;

import com.amazonaws.services.s3.AmazonS3;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private String awsAccessKey;
    private String awsSecretKey;
    private AmazonS3Config amazonS3Config;

    /* ---------------------------------------------------------------------------------------------------------------*/

    public String getAwsAccessKey() {
        return awsAccessKey;
    }

    public void setAwsAccessKey(String awsAccessKey) {
        this.awsAccessKey = awsAccessKey;
    }

    public String getAwsSecretKey() {
        return awsSecretKey;
    }

    public void setAwsSecretKey(String awsSecretKey) {
        this.awsSecretKey = awsSecretKey;
    }

    public AmazonS3Config getAmazonS3Config() {
        return amazonS3Config;
    }

    public void setAmazonS3Config(AmazonS3Config amazonS3Config) {
        this.amazonS3Config = amazonS3Config;
    }

    /* ---------------------------------------------------------------------------------------------------------------*/

    public static class AmazonS3Config {
        private String region;
        private String endpoint;
        private String bucketName;
        private String folderName;

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getEndpoint() {
            return endpoint;
        }

        public void setEndpoint(String endpoint) {
            this.endpoint = endpoint;
        }

        public String getBucketName() {
            return bucketName;
        }

        public void setBucketName(String bucketName) {
            this.bucketName = bucketName;
        }

        public String getFolderName() {
            return folderName;
        }

        public void setFolderName(String folderName) {
            this.folderName = folderName;
        }
    }
}
