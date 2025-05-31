package com.example.redistest.Service;

import com.example.redistest.Models.Products;
import com.example.redistest.Repository.ProductRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProductService {
    private ProductRepository productRepository;
    private RedisTemplate<String, Object> redisTemplate;

    public ProductService(ProductRepository productRepository, RedisTemplate<String, Object> redisTemplate) {
        this.productRepository = productRepository;
        this.redisTemplate = redisTemplate;
    }

    public Products getProduct(String productName, int id) {
        if(!productRepository.existsById(id)){
            throw new RuntimeException("Product not found");
        }
        String key = productName + ":" + id;
        Products product;

        if(redisTemplate.hasKey(key)){
            product = (Products) redisTemplate.opsForValue().get(key);
        }else{
            product = productRepository.findById(id).get();
        }
        return product;
    }

    public Products createProduct(Products product) {
        Products savedProduct = productRepository.save(product);
        String key = savedProduct.getName() + ":" + savedProduct.getId();
        redisTemplate.opsForValue().set(key, savedProduct); // syncing Redis with DB
        return savedProduct;
    }

    @Transactional(rollbackFor = Exception.class)
    public Products buyProduct(Products product, int quantity) {
        String key = product.getName() + ":" + product.getId();
        Products savedProduct;

        if (Boolean.TRUE.equals(redisTemplate.hasKey(key))) {
            savedProduct = (Products) redisTemplate.opsForValue().get(key);
        } else {
            savedProduct = productRepository.getProductsById(product.getId());
            redisTemplate.opsForValue().set(key, savedProduct);
        }

        if (savedProduct.getQuantity() < quantity) {
            redisTemplate.delete(key);
            throw new RuntimeException("Product out of stock or not enough quantity");
        }

        savedProduct.setQuantity(savedProduct.getQuantity() - quantity);
        Products updatedProduct = productRepository.save(savedProduct);
        redisTemplate.opsForValue().set(key, updatedProduct);

        return updatedProduct;
    }
}
