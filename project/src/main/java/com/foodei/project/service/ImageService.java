package com.foodei.project.service;

import com.foodei.project.entity.Image;
import com.foodei.project.entity.User;
import com.foodei.project.exception.BadRequestException;
import com.foodei.project.exception.StorageException;
import com.foodei.project.repository.ImageRepository;
import com.foodei.project.utils.FileUploadUtil;
import com.foodei.project.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.UUID;

@Service
public class ImageService {
    @Autowired
    private ImageRepository imageRepository;


    // Folder path để upload file
    private final Path rootPath = Paths.get("src/main/resources/static/uploads");


    public ImageService() {
        createFolder(rootPath);
    }

    // tạo folder nếu chưa tồn tại
    public void createFolder(Path path) {
        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Upload file
//    public Image uploadImage(MultipartFile file, User user) {
//        // Tạo folder tương ứng với id user
//        Path userDir = rootPath.resolve(String.valueOf(user.getId()));
////        createFolder(userDir);
//        try {
//            Files.createDirectories(userDir);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        // Validate file
//        validate(file);
//
//        // Tạo path tương ứng với fileUpload
//        String generateFileId = String.valueOf(Instant.now().getEpochSecond());
//        File fileServer = new File(userDir + "/" + generateFileId);
//
//        // Create image instance
//        Image image = new Image();
//
//        try {
//            // Use Buffer to store data
//            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(fileServer));
//            stream.write(file.getBytes());
//            stream.close();
//        } catch (Exception e) {
//            throw new StorageException("Errors occur while uploading file");
//        }
//        image.setUrl(fileServer.getPath());
//        image.setUser(user);
//        imageRepository.save(image);
//        return image;
//    }

    public Image uploadImage(MultipartFile file, User user) throws IOException{
        // 1: validate file
        validate(file);

        String userId = user.getId();
//        String uploadDir = rootPath.toString() + "/" + userId;
        Path uploadDir = Paths.get(rootPath.toString(),userId);
        String extension = Utils.getExtensionFile(file.getOriginalFilename());
        String fileName = userId + String.valueOf(Instant.now().getEpochSecond()) + "." + extension;
//        String urlImage = uploadDir + "/" + fileName;
        Path urlImage = Paths.get(uploadDir.toString(),fileName);

        FileUploadUtil.saveFile(uploadDir.toString(), fileName, file);

        Image image = Image.builder()
                .url(urlImage.toString())
                .user(user)
                .build();
        imageRepository.save(image);
        return image;

    }
    public void validate(MultipartFile file) {
        // Validate file name
        String fileName = file.getOriginalFilename();
        if (fileName == null || fileName.equals("")) {
            throw new BadRequestException("Invalid file name");
        }

        // Validate file extension
        String fileExtension = Utils.getExtensionFile(fileName);
        if (!Utils.checkFileExtenstion(fileExtension.toLowerCase())) {
            throw new BadRequestException("Invalid file");
        }

        // Kiểm tra size (upload dưới 2MB)
        if ((double) file.getSize() > (2 * 1024 * 1024)) {
            throw new BadRequestException("File must not excess 2MB");
        }
    }

    // Read image
    public byte[] readImage(String url) {
        // Lấy đường dẫn file
        Path path = Paths.get(url);

        // Kiểm tra path có tồn tại hay không
        if (!Files.exists(path)) {
            throw new StorageException("Errors occur while reading file " + url);
        }

        try {
            Resource resource = new UrlResource(path.toUri());

            if (resource.exists() || resource.isReadable()) {
                InputStream inputStream = resource.getInputStream();
                byte[] byteArray = StreamUtils.copyToByteArray(inputStream);
                inputStream.close(); // Remember to close InputStream
                return byteArray;
            } else {
                throw new StorageException("Errors occur while reading file " + url);
            }
        } catch (Exception e) {
            throw new StorageException("Errors occur while reading file " + url);
        }
    }

    // Delete image
    public void deleteImage(String url) {
        try {
            Files.deleteIfExists(Paths.get(url));
            Image image = imageRepository.findByUrl(url)
                    .orElseThrow(() -> new StorageException("Errors occur while deleting file " + url));
            imageRepository.delete(image);
        } catch (IOException e) {
            throw new StorageException("Errors occur while deleting file " + url);
        }
    }

    public Image findById(UUID id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new StorageException("Errors occur while reading file " + id));
    }

    public String showUrl(String url) {
        if (url == null) {
            return "";
        } else if (!url.contains("http")) {
            return "\\" + url;
        } else {
            return url;
        }
    }
}
