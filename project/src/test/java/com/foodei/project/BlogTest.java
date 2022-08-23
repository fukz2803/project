package com.foodei.project;

import com.foodei.project.entity.Blog;
import com.foodei.project.entity.User;
import com.foodei.project.request.BlogRequest;
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


    @Test
    void deleteBlog() {
        blogService.deleteBlog("0lpgu");
    }

    @Test
    void editBlog() {
        User user = userService.getUserById("2IVDW");
        Blog blog = blogService.getBlogById("39qC7");
        BlogRequest blogRequest = BlogRequest.builder()
                .title("edit blog test test test test test test").build();
        Blog blogedit = blogService.fromRequestToBlog(blogRequest);
        blogService.createAndEditSlug(blogedit);

    }
}
