package com.foodei.project;
import static org.assertj.core.api.Assertions.*;

import com.foodei.project.service.StorageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UploadTest {


    private final Path rootPath = Paths.get("src/main/resources/static/uploads");

    @Test
    void makeFolder() {
        try {
            Files.createDirectories(rootPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void makeFolder1() {
        Path path = Paths.get("src\\main\\resources\\static\\uploads\\ii5XE");
        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
