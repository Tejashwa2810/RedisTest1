package com.example.redistest.Service;

import com.example.redistest.Models.Products;
import com.example.redistest.Repository.ProductRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private ProductRepository productRepository;
    private RedisTemplate<String, Object> redisTemplate;

    public ProductService(ProductRepository productRepository, RedisTemplate<String, Object> redisTemplate) {
        this.productRepository = productRepository;
        this.redisTemplate = redisTemplate;
    }

    public Products getProduct(int id) {
        Products product = productRepository.getProductsById(id);
        return product;
    }

    public Products createProduct(Products product) {
        return productRepository.save(product);
    }
}
