package com.foodei.project.controller;

import com.foodei.project.entity.Blog;
import com.foodei.project.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class DashboardController {

    @Autowired
    private BlogService blogService;

    @GetMapping("/dashboard")
    public String getDashboardPage(Model model, @RequestParam(required = false, defaultValue = "") String keyword){
        model.addAttribute("keyword", keyword);
        return "dashboard/index";
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
