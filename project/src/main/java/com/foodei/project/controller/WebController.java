package com.foodei.project.controller;

import com.foodei.project.entity.Blog;
import com.foodei.project.entity.Category;
import com.foodei.project.service.BlogService;
import com.foodei.project.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
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
                              @RequestParam(required = false, defaultValue = "") String keyword){
        Page<Blog> blogPage = blogService.findAllBlogsPageContainTitle(0, 4, keyword);

        List<Blog> listBlogIndex = blogService.findAllBlogsIndex();
        model.addAttribute("listBlogIndex", listBlogIndex);

        List<Blog> blogListPage = blogPage.getContent();
        model.addAttribute("blogListPage",blogListPage);

        List<Blog> blogListHighestComment = blogService.getBlogsByHighComment();
        model.addAttribute("blogListHighestComment", blogListHighestComment);

        List<Category> categoryList = categoryService.findAllCategoryIndex();
        model.addAttribute("categoryList",categoryList);

        Blog blogHighestComment = blogService.getBlogHighestComment();
        model.addAttribute("blogHighestComment",blogHighestComment);

        List<Blog> blogsHeader = blogService.getBlogsHeader();
        model.addAttribute("blogsHeader",blogsHeader);

        model.addAttribute("blogService", blogService);
        model.addAttribute("keyword", keyword);

        return "web/index";
    }


    @GetMapping("/blogs")
    public String getBlogsPage(Model model,
                               @RequestParam(required = false, defaultValue = "") String keyword,
                               @RequestParam(required = false,defaultValue = "1") Integer page){
        Page<Blog> blogPage = blogService.findAllBlogsPageContainTitle(page - 1, 7, keyword);

        List<Blog> blogListPage = blogPage.getContent();
        model.addAttribute("blogListPage",blogListPage);

        List<Blog> blogListHighestComment = blogService.getBlogsByHighComment();
        model.addAttribute("blogListHighestComment", blogListHighestComment);

        List<Category> categoryList = categoryService.findAllCategoryIndex();
        model.addAttribute("categoryList",categoryList);

        Blog blogHighestComment = blogService.getBlogHighestComment();
        model.addAttribute("blogHighestComment",blogHighestComment);

        List<Blog> blogsHeader = blogService.getBlogsHeader();
        model.addAttribute("blogsHeader",blogsHeader);


        int totalPages = blogPage.getTotalPages();
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);
        model.addAttribute("blogService", blogService);
        return "web/blogs";
    }

    @GetMapping("/category/{name}")
    public String getCategoryPage2(Model model,
                                   @RequestParam(required = false, defaultValue = "") String keyword,
                                   @RequestParam(required = false,defaultValue = "1") Integer page,
                                   @PathVariable("name") String name){
        Page<Blog> blogPage = blogService.getAllBlogsByCategoryAndTitle(page - 1, 7 , keyword, name);

        List<Blog> blogListPage = blogPage.getContent();
        model.addAttribute("blogListPage",blogListPage);

        List<Blog> blogListHighestComment = blogService.getBlogsByHighComment();
        model.addAttribute("blogListHighestComment", blogListHighestComment);

        List<Category> categoryList = categoryService.findAllCategoryIndex();
        model.addAttribute("categoryList",categoryList);

        Blog blogHighestComment = blogService.getBlogHighestComment();
        model.addAttribute("blogHighestComment",blogHighestComment);

        List<Blog> blogsHeader = blogService.getBlogsHeader();
        model.addAttribute("blogsHeader",blogsHeader);


        int totalPages = blogPage.getTotalPages();
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);
        model.addAttribute("blogService", blogService);
        model.addAttribute("currentCategory", name);

        return "web/categories-list";
    }


    @GetMapping("/about")
    public String getAboutPage(Model model){
        List<Blog> blogsHeader = blogService.getBlogsHeader();
        model.addAttribute("blogsHeader",blogsHeader);
        return "web/about";
    }

    @GetMapping("/detail/{id}")
    public String getDetailPage(Model model,
                                @PathVariable("id") String id){
        Blog blog = blogService.getBlogById(id);
        model.addAttribute("blog", blog);
        return "web/single-post";
    }

    @GetMapping("/contact")
    public String getContactPage(Model model){
        List<Blog> blogsHeader = blogService.getBlogsHeader();
        model.addAttribute("blogsHeader",blogsHeader);
        return "web/contact";
    }


}
