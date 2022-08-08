package com.foodei.project.repository;

import com.foodei.project.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ImageRepository extends JpaRepository<Image, UUID> {
    List<Image> getImagesByUserId(Integer id);
}