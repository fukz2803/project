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

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BlogService {


    @Autowired
    private BlogRepository blogRepository;

    //Index Page

    public List<Blog> findAllBlogsIndex(){
        return blogRepository.findBlogsByStatusEqualsOrderByPublishedAtDesc();
    }

    public List<Blog> findAllBlogsIndexLimit(){
        return blogRepository.findByStatusEqualsOrderByPublishedAtDescLimit();
    }


    public Page<Blog> findAllBlogsPageContainTitle(int page, int pageSize, String title){
        Pageable pageable = PageRequest.of(page, pageSize);
        return blogRepository.getByTitleContainsIgnoreCaseAndStatusEqualsOrderByPublishedAtDesc(title, pageable);
    }

    public List<Blog> getBlogsByCategoryName(String name){
        return blogRepository.getByCategories_NameContainsIgnoreCaseAllIgnoreCase(name);
    }

    public List<Blog> getBlogsByHighComment(){
        List<Blog> blogs = findAllBlogsIndex();
        Collections.sort(blogs, new Comparator<Blog>() {
            @Override
            public int compare(Blog o1, Blog o2) {
                return Integer.compare(o2.getComments().size(), o1.getComments().size());
            }
        });
        return blogs;
    }

    public Blog getBlogHighestComment(){
        List<Blog> blogs = getBlogsByHighComment();
        return blogs.get(0);
    }

}
