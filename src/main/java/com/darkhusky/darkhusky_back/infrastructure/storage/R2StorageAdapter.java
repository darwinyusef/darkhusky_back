package com.darkhusky.darkhusky_back.infrastructure.storage;

import com.darkhusky.darkhusky_back.domain.port.out.StoragePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.io.InputStream;
import java.time.Duration;

@Component
public class R2StorageAdapter implements StoragePort {

    private final S3Client s3;
    private final S3Presigner presigner;
    private final String bucket;

    @Autowired
    public R2StorageAdapter(S3Client s3, S3Presigner presigner, String bucketName) {
        this.s3 = s3;
        this.presigner = presigner;
        this.bucket = bucketName;
    }

    @Override
    public String upload(String objectKey, InputStream data, long length, String contentType) throws Exception {
        PutObjectRequest req = PutObjectRequest.builder()
                .bucket(bucket)
                .key(objectKey)
                .contentType(contentType)
                .build();

        s3.putObject(req, RequestBody.fromInputStream(data, length));
        return objectKey;
    }

    @Override
    public void delete(String objectKey) throws Exception {
        DeleteObjectRequest req = DeleteObjectRequest.builder()
                .bucket(bucket)
                .key(objectKey)
                .build();

        s3.deleteObject(req);
    }

    @Override
    public String generatePresignedGetUrl(String objectKey, Duration duration) throws Exception {
        GetObjectRequest getReq = GetObjectRequest.builder()
                .bucket(bucket)
                .key(objectKey)
                .build();

        GetObjectPresignRequest presignReq = GetObjectPresignRequest.builder()
                .getObjectRequest(getReq)
                .signatureDuration(duration)
                .build();

        PresignedGetObjectRequest presigned = presigner.presignGetObject(presignReq);
        return presigned.url().toString();
    }

    @Override
    public PresignedUpload generatePresignedPutUrl(String objectKey, String contentType, Duration duration) throws Exception {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(objectKey)
                .contentType(contentType)
                .build();

        PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                .putObjectRequest(putObjectRequest)
                .signatureDuration(duration)
                .build();

        PresignedPutObjectRequest presigned = presigner.presignPutObject(presignRequest);
        return new PresignedUpload(presigned.url().toString(), objectKey);
    }
}
