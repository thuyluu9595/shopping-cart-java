package com.example.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@RestController
public class ProductController {

    private final ProductRepository productRepository;

    @Autowired
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/products")
    public List<Product> GetAllProducts(){
        return productRepository.findAll();
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Optional> GetProductById(@PathVariable Long id){
        Optional<Product> product = productRepository.findById(id);
        if(product.isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping("/products")
    public ResponseEntity<Product> CreateProduct(@RequestBody Product body){
        String productName = body.getName();
        if(productRepository.existsProductByName(productName)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        String code = UUID.randomUUID().toString();
        body.setCode(code);
        productRepository.save(body);
        return new ResponseEntity<>(body, HttpStatus.CREATED);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity DeleteProduct(@PathVariable Long id){
        Optional<Product> product = productRepository.findById(id);
        if(product.isPresent()){
            productRepository.deleteById(id);
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

}
