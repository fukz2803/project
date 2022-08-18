package com.foodei.project.service;

import com.foodei.project.entity.Blog;
import com.foodei.project.entity.Category;
import com.foodei.project.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BlogService {


    @Autowired
    private BlogRepository blogRepository;

    public List<Blog> findAll(){
        return blogRepository.findAll();
    }

    //Index Page
    public List<Blog> findAllBlogsIndex(){
        return blogRepository.findBlogsByStatusEqualsOrderByPublishedAtDesc();
    }

    public Page<Blog> findAllBlogsPageContainTitle(int page, int pageSize, String title){
        Pageable pageable = PageRequest.of(page, pageSize);
        return blogRepository.getByTitleContainsIgnoreCaseAndStatusEqualsOrderByPublishedAtDesc(title, pageable);
    }

    public List<Blog> getBlogsByCategoryName(String name){
        return blogRepository.getByCategories_NameContainsIgnoreCaseAllIgnoreCase(name);
    }

    public List<Blog> sortCommentBlog(){
        List<Blog> blogs = findAllBlogsIndex();
        Collections.sort(blogs, new Comparator<Blog>() {
            @Override
            public int compare(Blog o1, Blog o2) {
                return Integer.compare(o2.getComments().size(), o1.getComments().size());
            }
        });
        return blogs;
    }

    public List<Blog> getBlogsByHighComment(){
        List<Blog> blogs = sortCommentBlog();
        List<Blog> newblogs = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            newblogs.add(i - 1,blogs.get(i));
        }
        return newblogs;
    }

    public Blog getBlogHighestComment(){
        List<Blog> blogs = sortCommentBlog();
        return blogs.get(0);
    }

    public List<Blog> getBlogsHeader(){
        List<Blog> blogs = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            blogs.add(i,findAllBlogsIndex().get(i));
        }
        return blogs;
    }

    //Category Page
    public Page<Blog> getAllBlogsByCategoryAndTitle(int page, int pageSize, String title, String name){
        Pageable pageable = PageRequest.of(page, pageSize);
        return blogRepository.getByTitleContainsIgnoreCaseAndStatusEqualsAndCategories_NameOrderByPublishedAtDesc(title,name,pageable);
    }

    //Detail Page
    public Blog getBlogById(String id){
        return blogRepository.getBlogById(id);
    }

    //Dashboard - blogs
    public String showCategoryBlog(Blog blog){
        return String.join(", ", blog.getCategories().stream().map(Category::getName).toList());
    }

    public Page<Blog> findAllBlogsPageByTitle(int page, int pageSize, String title){
        Pageable pageable = PageRequest.of(page, pageSize);
        return blogRepository.findByTitleContainsIgnoreCaseOrderByCreateAtDesc(title, pageable);
    }

    //Dashboard - own blogs
    public Page<Blog> findAllBlogByUserId(int page, int pageSize, String title, String id){
        Pageable pageable = PageRequest.of(page, pageSize);
        return blogRepository.findByTitleContainsIgnoreCaseAndUser_IdOrderByPublishedAtDesc(title, id, pageable);
    }

    //Dashboard - delete blog
    public void deleteBlog(String id){
        List<Blog> blogs = findAll();
        blogs.removeIf(blog -> blog.getId().equals(id));
    }

    //Dashboard - own blogs
    public List<Blog> getBlogsByUserId(String id){
        return blogRepository.findByUser_Id(id);
    }
}
