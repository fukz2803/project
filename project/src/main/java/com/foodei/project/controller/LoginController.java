package com.foodei.project.controller;

import com.foodei.project.request.ForgotPassword;
import com.foodei.project.request.LoginRequest;
import com.foodei.project.request.UserRequest;
import com.foodei.project.service.AuthService;
import com.foodei.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;


@Controller
public class LoginController {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthService authService;


    @GetMapping("/login")
    public String getLoginPage(Model model){
        model.addAttribute("loginRequest", new LoginRequest("",""));
        return "dashboard/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute LoginRequest request, HttpSession session){

        authService.login(request, session);
        return "redirect:/";

    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        authService.logout(session);
        return "redirect:/";
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model){
        UserRequest userRequest = new UserRequest();
        model.addAttribute("userRequest",userRequest);
        return "dashboard/register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("userRequest") @Valid UserRequest userRequest){
        authService.registerNewUserAccount(userRequest);


        return "dashboard/register-verify" ;
    }

    @GetMapping("/confirm")
    public String confirmToken(@RequestParam("token") String token){
        authService.confirmToken(token);
        return "redirect:/";
    }


    @GetMapping("/forgot-password")
    public String getForgotPasswordPage(Model model){
        model.addAttribute("user", new ForgotPassword());
        return "dashboard/forgot-password";
    }

    @PostMapping("/forgot-password")
    public String resetPassword(@ModelAttribute("user") ForgotPassword forgotPassword){
        authService.resetPassword(forgotPassword.getEmail());
        return "dashboard/forgot-password-verify";
    }




}
