package com.example.product;

import com.example.product.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
//    public Product findByName(String name);
//
//    public boolean existsProductByName(String name);
//
//    @Query(value = "SELECT name FROM product", nativeQuery = true)
//    public List<String> findDistinctName();
}
