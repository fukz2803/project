package com.foodei.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class WebController {
    @GetMapping("/")
    public String getHomePage(){

        return "web/index";
    }

//    @GetMapping("/category/{name}")
//    public String getCategoryPage2(@PathVariable("name") String categoryName){
//        return "web/categories-list";
//    }

    @GetMapping("/category")
    public String getCa(){
        return "web/categories-list";
    }

    @GetMapping("/about")
    public String getAboutPage(){
        return "web/about";
    }

    @GetMapping("/detail")
    public String getDetailPage(){
        return "web/single-post";
    }

    @GetMapping("/contact")
    public String getContactPage(){
        return "web/contact";
    }

}
