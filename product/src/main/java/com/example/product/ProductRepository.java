package com.example.product;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    public Product findByName(String name);
    public boolean existsProductByName(String name);
}
