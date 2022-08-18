package com.foodei.project.controller;

import com.foodei.project.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class DashboardController {

    @Autowired
    private BlogService blogService;

    @GetMapping("/dashboard")
    public String getDashboardPage(Model model, @RequestParam(required = false, defaultValue = "") String keyword){
        model.addAttribute("keyword", keyword);
        return "dashboard/index";
    }

    @GetMapping("/dashboard/error")
    public String handleError(HttpServletRequest request){


        // test bug thì ở đây status = null ?
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return "error/404 - dashboard";
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return "error/500 - dashboard";
            } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                return "error/403 - dashboard";
            } else if (statusCode == HttpStatus.UNAUTHORIZED.value()) {
                return "error/401-dashboard";
            }
        }

        return "error/error - dashboard";
    }

}
