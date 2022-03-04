package com.example.s3publicpermission.web.dto;

import java.io.Serializable;

public class S3MetaData implements Serializable {

    private String key;
    private String url;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "S3MetaData{" +
                "key='" + key + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
