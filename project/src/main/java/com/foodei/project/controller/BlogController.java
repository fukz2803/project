package com.foodei.project.controller;

import com.foodei.project.entity.Blog;
import com.foodei.project.entity.Category;
import com.foodei.project.service.BlogService;
import com.foodei.project.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BlogController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private CategoryService categoryService;


    @GetMapping("/dashboard/blogs")
    public String getBlogListPage(Model model,
                                  @RequestParam(required = false, defaultValue = "") String keyword,
                                  @RequestParam(required = false,defaultValue = "1") Integer page){

        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);
        if (page < 1){
            return "redirect:/dashboard/error";
        }

        Page<Blog> blogPage = blogService.findAllBlogsPageByTitle(page - 1, 20, keyword);

        List<Blog> blogListPage = blogPage.getContent();
        model.addAttribute("blogListPage",blogListPage);

        model.addAttribute("blogService", blogService);

        int totalPages = blogPage.getTotalPages();
        model.addAttribute("totalPages", totalPages);

        return "dashboard/blogs";
    }

    @GetMapping("/dashboard/my-blogs/{id}")
    public String getMyBlogsPage(Model model,
                                 @PathVariable("id") String id,
                                 @RequestParam(required = false, defaultValue = "") String keyword,
                                 @RequestParam(required = false,defaultValue = "1") Integer page){
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);
//        if (page < 1){
//            return "dashboard/500";
//        }
        Page<Blog> blogPage = blogService.findAllBlogByUserId(page - 1, 20, keyword, id);

        List<Blog> blogListPage = blogPage.getContent();
        model.addAttribute("blogListPage",blogListPage);

        model.addAttribute("blogService", blogService);

        int totalPages = blogPage.getTotalPages();
        model.addAttribute("totalPages", totalPages);

        return "dashboard/my-blogs";
    }

    @GetMapping("/dashboard/blogs/create-blog")
    public String getCreateBlogPage(Model model){

        model.addAttribute("blog", new Blog());

        List<Category> categories = categoryService.findAllCategoryIndex();
        model.addAttribute("categories", categories);

        return "dashboard/blog-create";
    }

    @PostMapping("dashboard/blogs/create-blog")
    public String createBlog(@ModelAttribute Blog blog, BindingResult result){
        if (result.hasErrors()){
            return "redirect:/dashboard/create-blog";
        }

        return "redirect:/dashboard/blogs";
    }

    @GetMapping("/dashboard/blogs/detail/{id}/{slug}")
    public String getDetailBlogPage(Model model,
                                    @PathVariable("id") String id){

        Blog blog = blogService.getBlogById(id);
        model.addAttribute("blog", blog);

        List<Category> categories = categoryService.findAllCategoryIndex();
        model.addAttribute("categories", categories);

        return "dashboard/blog-detail";
    }


    @GetMapping("/dashboard/blogs/delete/{id}")
    public String deleteBlog(@PathVariable("id") String id){
        blogService.deleteBlog(id);
        return "redirect:/dashboard/blogs";
    }



}
