package com.foodei.project.controller;

import com.foodei.project.entity.Blog;
import com.foodei.project.entity.Category;
import com.foodei.project.entity.Image;
import com.foodei.project.request.BlogRequest;
import com.foodei.project.security.UserDetailCustom;
import com.foodei.project.service.BlogService;
import com.foodei.project.service.CategoryService;
import com.foodei.project.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class BlogController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ImageService imageService;


    @GetMapping("/dashboard/blogs")
    public String getBlogListPage(Model model,
                                  @RequestParam(required = false, defaultValue = "") String keyword,
                                  @RequestParam(required = false,defaultValue = "1") Integer page){

        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);
//        if (page < 1){
//            return "redirect:/dashboard/error";
//        }

        Page<Blog> blogPage = blogService.findAllBlogsPageByTitle(page - 1, 20, keyword);

        List<Blog> blogListPage = blogPage.getContent();
        model.addAttribute("blogListPage",blogListPage);

        model.addAttribute("blogService", blogService);

        int totalPages = blogPage.getTotalPages();
        model.addAttribute("totalPages", totalPages);

        return "dashboard/blogs";
    }

    @GetMapping("/dashboard/my-blogs")
    public String getMyBlogsPage(Model model,
                                 @RequestParam(required = false, defaultValue = "") String keyword,
                                 @RequestParam(required = false,defaultValue = "1") Integer page){
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);

        // Lấy ra thông tin user đang đăng nhập
        UserDetailCustom user = (UserDetailCustom) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Page<Blog> blogPage = blogService.findAllBlogByUserId(page - 1, 20, keyword, user.getUser().getId());

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

    @PostMapping("/dashboard/blogs/create-blog")
    public String createBlog(@ModelAttribute Blog blog, BindingResult result){
        if (result.hasErrors()){
            return "/dashboard/blogs/create-blog";
        }

        return "redirect:/dashboard/blogs";
    }

    @GetMapping("/dashboard/blogs/detail/{id}")
    public String getDetailBlogPage(Model model,
                                    @PathVariable("id") String id){

        Blog blog = blogService.getBlogById(id);
        model.addAttribute("blog", blog);

        BlogRequest blogRequest = blogService.toBlogRequest(blog);
        model.addAttribute("blogRequest",blogRequest);

        String thumbnail = imageService.showUrl(blogRequest.getThumbnail());
        model.addAttribute("thumbnail", thumbnail);

        List<Category> categories = categoryService.findAllCategoryIndex();
        model.addAttribute("categories", categories);

        return "dashboard/blog-detail";
    }

    @PostMapping("/dashboard/blogs/edit/{id}")
    public String editBlog(@PathVariable("id") String id, @ModelAttribute BlogRequest blogRequest, BindingResult result){
        if (result.hasErrors()){
            return "/dashboard/blogs/detail/" + id;
        }

        // Lấy ra thông tin user đang đăng nhập
        UserDetailCustom user = (UserDetailCustom) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Image image;
//        if (blogRequest.getImage() != null && !blogRequest.getImage().isEmpty()){
////            image = imageService.uploadImage(blogRequest.getImage(), user.getUser());
//            blogRequest.setThumbnail(image.getUrl());
//        }

        Blog blog = blogService.fromRequestToBlog(blogRequest);
        blogService.createAndEditSlug(blog);

        return "redirect:/dashboard/blogs/detail/" + id;
    }




    @GetMapping("/dashboard/blogs/delete/{id}")
    public String deleteBlog(@PathVariable("id") String id, HttpServletRequest request){
        String referer = request.getHeader("Referer");
        blogService.deleteBlog(id);
        return "redirect:" + referer;
    }



}
