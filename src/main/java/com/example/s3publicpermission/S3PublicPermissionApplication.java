package com.example.s3publicpermission;

import com.example.s3publicpermission.config.ApplicationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({ApplicationProperties.class})
public class S3PublicPermissionApplication {

    public static void main(String[] args) {
        SpringApplication.run(S3PublicPermissionApplication.class, args);
    }

}
