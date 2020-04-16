package com.example.demo.controller;

import com.example.demo.model.Category;
import com.example.demo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/category-list")
    public ModelAndView findAll() {
        ModelAndView modelAndView = new ModelAndView("category/list");
        List<Category> categoryList = this.categoryService.findAll();
        modelAndView.addObject("categoryList", categoryList);
        return modelAndView;
    }


}
