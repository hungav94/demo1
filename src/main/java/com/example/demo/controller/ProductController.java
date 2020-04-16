package com.example.demo.controller;

import com.example.demo.model.Category;
import com.example.demo.model.Product;
import com.example.demo.model.form.ProductForm;
import com.example.demo.service.CategoryService;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@PropertySource("classpath:application.properties")
public class ProductController {

    @Autowired
    private Environment environment;

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @ModelAttribute("categoryList")
    public List<Category> categoryList() {
        return this.categoryService.findAll();
    }

    @GetMapping("/product-list")
    public ModelAndView findAllProduct() {
        ModelAndView modelAndView = new ModelAndView("product/list");
        List<Product> productList = this.productService.findAll();
        modelAndView.addObject("productList", productList);
        return modelAndView;
    }

    @GetMapping("/product-insert")
    public ModelAndView showFormInsertProduct() {
        ModelAndView modelAndView = new ModelAndView("product/insert");
        modelAndView.addObject("product", new Product());
        return modelAndView;
    }

    @PostMapping("/product-insert")
    public ModelAndView insertProduct(@ModelAttribute("product") ProductForm productForm) throws IOException {
        MultipartFile multipartFile = productForm.getImage();
        String fileName = multipartFile.getOriginalFilename();
        String fileUpload = environment.getProperty("uploadPath").toString();

        FileCopyUtils.copy(productForm.getImage().getBytes(), new File(fileUpload + fileName));

        Product product = new Product();
        product.setName(productForm.getName());
        product.setDescription(productForm.getDescription());
        product.setPrice(productForm.getPrice());
        product.setCategory(productForm.getCategory());
        product.setImage(fileName);

        productService.save(product);
        ModelAndView modelAndView = new ModelAndView("redirect:/product-list");
        modelAndView.addObject("product", new Product());

        return modelAndView;
    }
}
