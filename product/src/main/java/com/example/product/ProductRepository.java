package com.example.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
//    public Product findByName(String name);
//
//    public boolean existsProductByName(String name);
//
//    @Query(value = "SELECT name FROM product", nativeQuery = true)
//    public List<String> findDistinctName();
}
