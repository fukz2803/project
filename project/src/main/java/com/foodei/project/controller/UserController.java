package com.foodei.project.controller;

import com.foodei.project.entity.Blog;
import com.foodei.project.entity.User;
import com.foodei.project.service.BlogService;
import com.foodei.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private BlogService blogService;


    @GetMapping("/dashboard/admin/users")
    public String getUsersListPage(Model model,
                                   @RequestParam(required = false, defaultValue = "") String keyword,
                                   @RequestParam(required = false,defaultValue = "1") Integer page){
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);
        if (page < 1){
            return "dashboard/404";
        }

        Page<User> userPage = userService.findAllUserByNameAndEmail(page - 1, 20, keyword, keyword);

        List<User> userList = userPage.getContent();
        model.addAttribute("userList",userList);

        int totalPages = userPage.getTotalPages();
        model.addAttribute("totalPages", totalPages);

        return "dashboard/users";
    }

    @GetMapping("/dashboard/admin/user/{id}")
    public String getUserDetail(Model model,
                                @PathVariable("id") String id){
        User user = userService.getUserById(id);
        model.addAttribute("user", user);

        List<Blog> blogs = blogService.getBlogsByUserId(id);
        model.addAttribute("blogs", blogs);

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
