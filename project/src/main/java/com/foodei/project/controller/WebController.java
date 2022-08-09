package com.foodei.project.controller;

import com.foodei.project.dto.BlogInfo;
import com.foodei.project.entity.Blog;
import com.foodei.project.entity.Category;
import com.foodei.project.service.BlogService;
import com.foodei.project.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class WebController {


    @Autowired
    private BlogService blogService;
    @Autowired
    private CategoryService categoryService;


    @GetMapping("/")
    public String getHomePage(Model model,
                              @RequestParam(required = false, defaultValue = "") String keyword,
                              @RequestParam(required = false,defaultValue = "1") Integer page){
//        Page<BlogInfo> blogPage = blogService.getAllBlogIndex(page -1, 7);

//        int totalPages = blogPage.getTotalPages();
//        model.addAttribute("totalPages", totalPages);

        List<Blog> blogList = blogService.findAllBlogsIndex();
        model.addAttribute("blogList", blogList);

        List<Category> categoryList = categoryService.findAllCategoryIndex();
        model.addAttribute("categoryList",categoryList);

        Blog blogHighestComment = blogService.getBlogHighestComment();
        model.addAttribute("blogHighestComment",blogHighestComment);

        model.addAttribute("blogService", blogService);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);


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
