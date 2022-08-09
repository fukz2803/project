package com.foodei.project.service;

import com.foodei.project.entity.Category;
import com.foodei.project.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> findAllCategoryIndex(){
        return categoryRepository.findAll();
    }
}
