package com.foodei.project.controller;

import com.foodei.project.entity.Blog;
import com.foodei.project.entity.User;
import com.foodei.project.service.BlogService;
import com.foodei.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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
            return "error/404";
        }

        Page<User> userPage = userService.findAllUserByNameAndEmail(page - 1, 20, keyword, keyword);

        List<User> userList = userPage.getContent();
        model.addAttribute("userList",userList);

        model.addAttribute("userService", userService);

        int totalPages = userPage.getTotalPages();
        model.addAttribute("totalPages", totalPages);

        return "dashboard/users";
    }

    @GetMapping("/dashboard/user/detail/{id}")
    public String getUserDetail(Model model,
                                @PathVariable("id") String id){
        // Lấy ra thông tin user đang đăng nhập
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!currentUser.getRole().contains("ADMIN")){
            return "error/403";
        }

        User user = userService.getUserById(id);
        model.addAttribute("user", user);

        List<Blog> blogs = blogService.getBlogsByUserId(id);
        model.addAttribute("blogs", blogs);

        return "dashboard/profile";
    }

    @GetMapping("/dashboard/user/profile")
    public String getUserDetail(Model model){
        // Lấy ra thông tin user đang đăng nhập
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", user);

        List<Blog> blogs = blogService.getBlogsByUserId(user.getId());
        model.addAttribute("blogs", blogs);

        model.addAttribute("userService", userService);
//        model.addAttribute("updateUser")

        return "dashboard/profile";
    }

    @PostMapping("/dashboard/user/update-user")
    public String updateUser(Model model){


        return "dashboard/profile";
    }

    @GetMapping("/dashboard/admin/user-active/{id}")
    public String avticeUser(@PathVariable("id") String id){
        User user = userService.getUserById(id);
        userService.enableUser(user.getEmail());

        return "redirect:/dashboard/admin/users";
    }

    @GetMapping("/dashboard/admin/user-disable/{id}")
    public String disableUser(@PathVariable("id") String id){
        User user = userService.getUserById(id);
        userService.disableUser(user.getEmail());

        return "redirect:/dashboard/admin/users";
    }

}
