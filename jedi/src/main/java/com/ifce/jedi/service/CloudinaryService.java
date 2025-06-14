package com.ifce.jedi.service;

import com.cloudinary.Cloudinary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public Map<String, String> uploadImage(MultipartFile file) throws IOException {
        Map<String, Object> options = Map.of("folder", "JEDI");

        Map<?, ?> result = cloudinary.uploader().upload(file.getBytes(), options);
        return Map.of(
                "url", (String) result.get("secure_url"),
                "public_id", (String) result.get("public_id")
        );
    }
    public void deleteImage(String publicId) throws IOException {
        cloudinary.uploader().destroy(publicId, Map.of());
    }
}