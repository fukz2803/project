package com.foodei.project.service;

import com.foodei.project.entity.Category;
import com.foodei.project.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> findAll(){
        return categoryRepository.findAll();
    }

    public List<Category> findAllCategoryIndex(){
        return categoryRepository.findAll();
    }

    public Page<Category> findAllCategory(int page, int pageSize, String name){
        Pageable pageable = PageRequest.of(page, pageSize);
        return categoryRepository.findByNameContainsIgnoreCase(name, pageable);
    }

    public void deleteCategory(Integer id){
        List<Category> categories = findAll();
        categories.removeIf(category -> category.getId() == id);
    }
}
