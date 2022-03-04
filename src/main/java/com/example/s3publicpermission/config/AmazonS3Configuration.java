package com.example.s3publicpermission.config;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonS3Configuration {

    private String awsAccessKey;
    private String awsSecretKey;
    private String region;
    private String endpoint;
    private final ApplicationProperties properties;

    public AmazonS3Configuration(ApplicationProperties properties) {
        this.properties = properties;
        this.awsAccessKey = properties.getAwsAccessKey();
        this.awsSecretKey = properties.getAwsSecretKey();
        this.region = properties.getAmazonS3Config().getRegion();
        this.endpoint = properties.getAmazonS3Config().getEndpoint();
    }

    @Bean
    public AmazonS3 amazonS3() {
        AWSCredentialsProvider provider = new AWSStaticCredentialsProvider(new BasicAWSCredentials(awsAccessKey, awsSecretKey));
        AwsClientBuilder.EndpointConfiguration config = new AwsClientBuilder.EndpointConfiguration(endpoint, region);
        AmazonS3ClientBuilder s3ClientBuilder = AmazonS3ClientBuilder.standard();
        s3ClientBuilder.setCredentials(provider);
        s3ClientBuilder.setEndpointConfiguration(config);
        return s3ClientBuilder.build();
    }
}
