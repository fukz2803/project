package com.foodei.project.controller;

import com.foodei.project.entity.Category;
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
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/dashboard/categories")
    public String getCategoriesPage(Model model,
                                    @RequestParam(required = false, defaultValue = "") String keyword,
                                    @RequestParam(required = false,defaultValue = "1") Integer page){
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);
//        if (page < 1){
//            return "dashboard/404";
//        }

        Page<Category> categoryPage = categoryService.findAllCategory(page - 1, 10, keyword);

        List<Category> categoryList = categoryPage.getContent();
        model.addAttribute("categoryList", categoryList);

        int totalPages = categoryPage.getTotalPages();
        model.addAttribute("totalPages", totalPages);

        return "dashboard/categories";
    }

    @GetMapping("/dashboard/categories/delete/{id}")
    public String deleteCategory(@PathVariable("id") Integer id){
        categoryService.deleteCategory(id);
        return "redirect:/dashboard/categories";
    }

}
