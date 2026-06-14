package com.hanpyeon.academyapi.media.storage;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("deploy")
class AmazonS3Config {
    private final String accessKey;
    private final String secretKey;
    private final String region;

    public AmazonS3Config(@Value("${cloud.aws.credentials.access-key}") String accessKey, @Value("${cloud.aws.credentials.secret-key}") String secretKey, @Value("${cloud.aws.region.static}") String region) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.region = region;
    }

    @Bean
    public AmazonS3Client amazonS3Client() {
        BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(accessKey, secretKey);
        return new AmazonS3Client(basicAWSCredentials);
    }
}
