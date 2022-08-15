package com.foodei.project.controller;

import com.foodei.project.dto.UserDto;
import com.foodei.project.entity.User;
import com.foodei.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

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
}
