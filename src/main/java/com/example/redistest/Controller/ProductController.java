package com.example.redistest.Controller;

import com.example.redistest.Models.Products;
import com.example.redistest.Service.ProductService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {
    private ProductService productService;
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public Products getProduct(@PathVariable int id) {
        return productService.getProduct(id);
    }

    @PostMapping("/create")
    public Products createProduct(@RequestBody Products product) {
        return productService.createProduct(product);
    }
}
