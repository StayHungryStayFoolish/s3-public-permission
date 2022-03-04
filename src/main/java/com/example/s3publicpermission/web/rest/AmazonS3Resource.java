package com.example.s3publicpermission.web.rest;

import com.example.s3publicpermission.service.AmazonS3Service;
import com.example.s3publicpermission.web.dto.S3MetaData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class AmazonS3Resource {

    private final AmazonS3Service s3Service;

    public AmazonS3Resource(AmazonS3Service s3Service) {
        this.s3Service = s3Service;
    }

    @PostMapping("/upload")
    public ResponseEntity<S3MetaData> upload(@RequestParam MultipartFile file) {
        S3MetaData s3MetaData = s3Service.uploadObject(file);
        return ResponseEntity.ok(s3MetaData);
    }
}
