package com.example.redistest.Service;

import com.example.redistest.Models.Products;
import com.example.redistest.Repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private ProductRepository productRepository;
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Products getProduct(int id) {
        Products product = productRepository.getProductsById(id);
        return product;
    }

    public Products createProduct(Products product) {
        return productRepository.save(product);
    }
}
