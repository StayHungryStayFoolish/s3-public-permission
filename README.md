# S3 Public Permission Best Practices

## Amazon S3 Endpoint

https://docs.aws.amazon.com/zh_cn/general/latest/gr/s3.html

## Prerequisites

- create IAM User

- configurate S3 Bucket policy

### IAM

1. Login AWS Managed Console
2. Find IAM
3. Create IAM Policy
4. Create IAM User (add IAM Policy to IAM User)
5. Get IAM User Security credentials (access key / secret key) 

**IAM Policy Json** 

```json
{
    "Version": "2012-10-17",
    "Statement": [
        {
            "Sid": "VisualEditor0",
            "Effect": "Allow",
            "Action": [
                "s3:PutObject",
                "s3:GetObject",
                "s3:PutBucketTagging",
                "s3:CreateBucket",
                "s3:GetObjectTagging",
                "s3:ListBucket",
                "s3:PutObjectTagging"
            ],
            "Resource": [
                "arn:aws:s3:::img-hosting-site",
                "arn:aws:s3:::img-hosting-site/*"
            ]
        },
        {
            "Sid": "VisualEditor1",
            "Effect": "Allow",
            "Action": "s3:ListAllMyBuckets",
            "Resource": "*"
        }
    ]
}
```

### S3 Bucket Policy

1. create S3 bucket use name `img-hosting-site`
2. create S3 bucket folder `markdown-folder` in S3 bucket `img-hosting-site`
3. configurate S3 bucket policy

**S3 bucket policy**

- Principal: allow anybody
- Action: allow read s3 object
- Resource: specify resources
- Condition: specify the conditions for allowing anybody to read, only if the resource`s tag key is public and value is yes ( S3 object tag: public = yes)

```json
{
    "Version": "2012-10-17",
    "Statement": [
        {
            "Effect": "Allow",
            "Principal": "*",
            "Action": "s3:GetObject",
            "Resource": "arn:aws:s3:::img-hosting-site/markdown-folder/*",
            "Condition": {
                "StringEquals": {
                    "s3:ExistingObjectTag/public": "yes"
                }
            }
        }
    ]
}
```

## Upload read-only S3 object

ApplicationProperties.class
- mapping application.yml

config.AmazonS3Configuration.class

```java
    @Bean
    public AmazonS3 amazonS3() {
        AWSCredentialsProvider provider = new AWSStaticCredentialsProvider(new BasicAWSCredentials(awsAccessKey, awsSecretKey));
        AwsClientBuilder.EndpointConfiguration config = new AwsClientBuilder.EndpointConfiguration(endpoint, region);
        AmazonS3ClientBuilder s3ClientBuilder = AmazonS3ClientBuilder.standard();
        s3ClientBuilder.setCredentials(provider);
        s3ClientBuilder.setEndpointConfiguration(config);
        return s3ClientBuilder.build();
    }
```

service.AmazonS3Service.class

```java
    PutObjectRequest putObjectRequest = new PutObjectRequest(s3FilePath, uuidName, file.getInputStream(), metadata);
    putObjectRequest.setTagging(new ObjectTagging(tags));
    amazonS3.putObject(putObjectRequest);
    GeneratePresignedUrlRequest urlRequest = new GeneratePresignedUrlRequest(s3FilePath, uuidName);
    URL url = amazonS3.generatePresignedUrl(urlRequest);
```

web.rest.AmazonS3Resource.class

```java
    @PostMapping("/upload")
    public ResponseEntity<S3MetaData> upload(@RequestParam MultipartFile file) {
        S3MetaData s3MetaData = s3Service.uploadObject(file);
        return ResponseEntity.ok(s3MetaData);
    }
```

## S3 Object Lifecycle

https://docs.aws.amazon.com/zh_cn/AmazonS3/latest/userguide/storage-class-intro.html

https://docs.aws.amazon.com/zh_cn/AmazonS3/latest/userguide/lifecycle-transition-general-considerations.html

![s3](https://docs.aws.amazon.com/zh_cn/AmazonS3/latest/userguide/images/lifecycle-transitions-v3.png)