package com.foodei.project.service;

import com.foodei.project.dto.BlogInfo;
import com.foodei.project.entity.Blog;
import com.foodei.project.entity.Comment;
import com.foodei.project.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BlogService {


    @Autowired
    private BlogRepository blogRepository;

//    public Page<BlogInfo> getAllBlogIndex(int page, int pageSize){
//        Pageable pageable = PageRequest.of(page, pageSize);
//
//        return blogRepository.getAllBlogInfo(pageable);
//    }

//    public List<BlogInfo> getAllBlogInfo(){
//        return blogRepository.getAllBlogInfo();
//    }

    public List<Blog> findAllBlogs(){
        return blogRepository.findAll();
    }


}
