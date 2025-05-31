package com.example.redistest.Controller;

import com.example.redistest.DTOs.ProductBuyRequestDTO;
import com.example.redistest.Models.Products;
import com.example.redistest.Repository.ProductRepository;
import com.example.redistest.Service.ProductService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {
    private ProductService productService;
    private ProductRepository productRepository;
    public ProductController(ProductService productService, ProductRepository productRepository) {
        this.productService = productService;
        this.productRepository = productRepository;
    }

    @GetMapping("/{id}")
    public Products getProduct(@PathVariable int id) {
        Products product = productRepository.getProductsById(id);
        return productService.getProduct(product.getName(), id);
    }

    @PostMapping("/create")
    public Products createProduct(@RequestBody Products product) {
        return productService.createProduct(product);
    }

    @PostMapping("/buy")
    public Products buyProduct(@RequestBody ProductBuyRequestDTO dto) {
        Products product = productRepository.getProductsById(dto.getProductId());

        if (product == null) {
            throw new RuntimeException("Product not found");
        }

        return productService.buyProduct(product, dto.getQuantity());
    }

}
