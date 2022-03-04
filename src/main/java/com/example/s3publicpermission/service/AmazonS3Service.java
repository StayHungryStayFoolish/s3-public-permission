package com.example.s3publicpermission.service;

import com.amazonaws.services.mq.model.InternalServerErrorException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.services.s3control.model.PutBucketLifecycleConfigurationRequest;
import com.example.s3publicpermission.config.ApplicationProperties;
import com.example.s3publicpermission.util.StringConvertUtil;
import com.example.s3publicpermission.web.dto.S3MetaData;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AmazonS3Service {

    private final ApplicationProperties properties;
    private final AmazonS3 amazonS3;
    private String bucketName;
    private String folderName;
    private String s3FilePath;

    public AmazonS3Service(ApplicationProperties properties, AmazonS3 amazonS3) {
        this.properties = properties;
        this.amazonS3 = amazonS3;
        this.bucketName = properties.getAmazonS3Config().getBucketName();
        this.folderName = properties.getAmazonS3Config().getFolderName();
        this.s3FilePath = bucketName.concat("/").concat(folderName);
    }

    public S3MetaData uploadObject(MultipartFile file) {
        S3MetaData s3MetaData = new S3MetaData();
        String fileName = file.getOriginalFilename();
        String extension = StringConvertUtil.getExtensionName(fileName);
        String uuidName = UUID.randomUUID().toString().replaceAll("-", "").concat(extension);
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(getContentType(fileName));
        metadata.setContentLength(file.getSize());
        try {
            // set s3 object tags
            List<Tag> tags = new ArrayList<>();
            tags.add(new Tag("public", "yes"));
            // package request
            PutObjectRequest putObjectRequest = new PutObjectRequest(s3FilePath, uuidName, file.getInputStream(), metadata);
            putObjectRequest.setTagging(new ObjectTagging(tags));
            amazonS3.putObject(putObjectRequest);
            GeneratePresignedUrlRequest urlRequest = new GeneratePresignedUrlRequest(s3FilePath, uuidName);
            URL url = amazonS3.generatePresignedUrl(urlRequest);
            if (null == url) {
                throw new InternalServerErrorException("Can not get s3 file url!");
            }
            String fileUrl = StringConvertUtil.getBackAtSymbol(url.toString(), ":");
            fileUrl = StringConvertUtil.getFrontAtSymbol(fileUrl, "?");
            s3MetaData.setKey(uuidName);
            s3MetaData.setUrl(fileUrl);
            return s3MetaData;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getContentType(String fileName) {
        String fileExtension = fileName.substring(fileName.lastIndexOf("."));
        if (".bmp".equalsIgnoreCase(fileExtension)) {
            return "image/bmp";
        }
        if (".gif".equalsIgnoreCase(fileExtension)) {
            return "image/gif";
        }
        if (".jpeg".equalsIgnoreCase(fileExtension) || ".jpg".equalsIgnoreCase(fileExtension) || ".png".equalsIgnoreCase(fileExtension)) {
            return "image/jpeg";
        }
        if (".html".equalsIgnoreCase(fileExtension)) {
            return "text/html";
        }
        if (".txt".equalsIgnoreCase(fileExtension)) {
            return "text/plain";
        }
        if (".vsd".equalsIgnoreCase(fileExtension)) {
            return "application/vnd.visio";
        }
        if (".ppt".equalsIgnoreCase(fileExtension) || "pptx".equalsIgnoreCase(fileExtension)) {
            return "application/vnd.ms-powerpoint";
        }
        if (".doc".equalsIgnoreCase(fileExtension) || "docx".equalsIgnoreCase(fileExtension)) {
            return "application/msword";
        }
        if (".pdf".equalsIgnoreCase(fileExtension)) {
            return "application/pdf";
        }
        if (".xml".equalsIgnoreCase(fileExtension)) {
            return "text/xml";
        }
        if (".xls".equalsIgnoreCase(fileExtension)) {
            return "application/vnd.ms-excel";
        }
        if (".xlsx".equalsIgnoreCase(fileExtension)) {
            return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        }
        if (".svg".equalsIgnoreCase(fileExtension)) {
            return "image/svg+xml; charset=UTF-8";
        }
        return "image/jpeg";
    }

}
