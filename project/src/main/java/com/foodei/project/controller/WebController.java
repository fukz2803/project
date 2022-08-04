package com.foodei.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {
    @GetMapping("/")
    public String getHomePage(){

        return "web/index";
    }

    @GetMapping("/about")
    public String getAboutPage(){
        return "web/about";
    }

    @GetMapping("/contact")
    public String getContactPage(){
        return "web/contact";
    }

}
