package com.example.redistest.Repository;

import com.example.redistest.Models.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Products, Integer> {
    Products getProductsById(int id);
}
