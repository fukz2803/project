package com.foodei.project.service;

import com.foodei.project.entity.Category;
import com.foodei.project.repository.CategoryRepository;
import com.foodei.project.request.CategoryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private BlogService blogService;

    public List<Category> findAllCategoryIndex(){
        return categoryRepository.findAll();
    }

    public Page<Category> findAllCategory(int page, int pageSize, String name){
        Pageable pageable = PageRequest.of(page, pageSize);
        return categoryRepository.findByNameContainsIgnoreCase(name, pageable);
    }


    public Optional<Category> findById(String id){
        return categoryRepository.findById(id);
    }

    public void deleteCategory(String id){
        Optional<Category> category = findById(id);
        category.ifPresent(value -> categoryRepository.delete(value));
    }

    public CategoryRequest toCategoryRequest(Category category){
        return CategoryRequest.builder()
                .id(category.getId())
                .name(category.getName())
                .avatar(category.getAvatar())
                .build();
    }

    public Category fromRequestToCategory(CategoryRequest categoryRequest){

        // Kiểm tra có thay đổi avatar không
        String avatar = categoryRequest.getAvatar();
        if (avatar == null && categoryRequest.getId() != null){
            //Nếu avatar không thay đổi thì lấy avatar đang có
            avatar = findById(categoryRequest.getId()).get().getAvatar();
        }

        return Category.builder()
                .id(categoryRequest.getId())
                .name(categoryRequest.getName())
                .avatar(avatar)
                .build();
    }

    public Category createAndEdit(Category category){
        return categoryRepository.save(category);
    }
}
