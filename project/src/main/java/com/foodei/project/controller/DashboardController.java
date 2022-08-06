package com.foodei.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {
    @GetMapping("/dashboard")
    public String getDashboardPage(){
        return "dashboard/index";
    }

    @GetMapping("/dashboard/blogs")
    public String getBlogListPage(){
        return "dashboard/blogs";
    }

    @GetMapping("/dashboard/my-blogs")
    public String getMyBlogsPage(){
        return "dashboard/my-blogs";
    }

    @GetMapping("/dashboard/create-blog")
    public String getCreateBlogPage(){
        return "dashboard/create-blog";
    }

    @GetMapping("/dashboard/users")
    public String getUserListPage(){
        return "dashboard/users";
    }

    @GetMapping("/dashboard/create-user")
    public String getCreateUserPage(){
        return "dashboard/create-user";
    }

    @GetMapping("/dashboard/user")
    public String getUserProfilePage(){
        return "dashboard/profile";
    }

    @GetMapping("/dashboard/register")
    public String getRegisterPage(){
        return "dashboard/register";
    }

    @GetMapping("/dashboard/login")
    public String getLoginPage(){
        return "dashboard/login";
    }

    @GetMapping("/dashboard/forgot-password")
    public String getForgotPasswordPage(){
        return "dashboard/forgot-password";
    }
}
