package com.foodei.project;

import com.foodei.project.entity.Blog;
import com.foodei.project.service.BlogService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BlogTest {

    @Autowired
    private BlogService blogService;
//
//    @Test
//    void deleteBlog() {
//        Blog blog = blogService.getBlogById("123");
//        blogService.deleteBlog(blog);
//    }
}
