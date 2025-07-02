package com.ifce.jedi.service;

import com.ifce.jedi.exception.custom.ImageDeletionException;
import com.ifce.jedi.exception.custom.ImageUploadException;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
public class MinioService {

    @Value("${minio.bucket}")
    private String minioBucket;

    @Value("${minio.public-endpoint}")
    private String minioPublicEndpoint;

    private final MinioClient minioClient;

    public MinioService(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    public Map<String, String> create(MultipartFile file) {
        try {
            var filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();

            minioClient.putObject(
                    PutObjectArgs
                            .builder()
                            .bucket(minioBucket)
                            .object(filename)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );

            String publicUrl = String.format("%s/%s/%s",
                    minioPublicEndpoint, minioBucket, filename);

            return Map.of(
                    "url", publicUrl,
                    "filename", filename
            );

        } catch (Exception e) {
            throw new ImageUploadException();
        }
    }

    public void deleteImage(String filename) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs
                            .builder()
                            .bucket(minioBucket)
                            .object(filename)
                            .build()
            );
        } catch (Exception e) {
            throw new ImageDeletionException();
        }
    }
}
