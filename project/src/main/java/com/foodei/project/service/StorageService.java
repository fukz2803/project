package com.foodei.project.service;

import com.foodei.project.entity.User;
import com.foodei.project.exception.StorageException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class StorageService {
    @Value("${upload.path}")
    private String path;

    public String saveFile(MultipartFile file, User user) {
        if (file.isEmpty()) {
            throw new StorageException("Failed to store empty file");
        }

        String userId = user.getId();
        String extension = getFileExtension(file.getOriginalFilename());
        String newfileName = path + userId + "." + extension;

        InputStream is = null;
        try {
            is = file.getInputStream();
            Files.copy(is, Paths.get(newfileName), StandardCopyOption.REPLACE_EXISTING);
            return userId + "." + extension;
        } catch (IOException e) {
            var msg = String.format("Failed to store file %s", newfileName);
            throw new RuntimeException(e);
        }
    }

    public String getFileExtension(String fileName) {
        int postOfDot = fileName.lastIndexOf(".");
        if (postOfDot >= 0) {
            return fileName.substring(postOfDot + 1);
        } else {
            return null;
        }
    }
}
