package com.foodei.project;

import com.foodei.project.entity.User;
import com.foodei.project.service.BlogService;
import com.foodei.project.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BlogTest {

    @Autowired
    private BlogService blogService;
    @Autowired
    private UserService userService;

//
//    @Test
//    void deleteBlog() {
//        Blog blog = blogService.getBlogById("123");
//        blogService.deleteBlog(blog);
//    }

    @Test
    void editBlog() {
        User user = userService.getUserById("1zFoo");

    }
}
