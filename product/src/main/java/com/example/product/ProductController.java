package com.example.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@RestController
@RequestMapping("/api/products")
public class ProductController {
//
//    private final ProductRepository productRepository;
//
//    @Autowired
//    public ProductController(ProductRepository productRepository) {
//        this.productRepository = productRepository;
//    }
//
//    // /products
//    @GetMapping("/products")
//    public List<Product> GetAllProducts(){
//        /*
//         Get all products
//         */
//        return productRepository.findAll();
//    }
//
//    // /products/<long:id>
//    @GetMapping("/products/{id}")
//    public ResponseEntity<Optional> GetProductById(@PathVariable Long id){
//        /*
//         Get product by id
//         */
//        Optional<Product> product = productRepository.findById(id);
//        if(product.isEmpty()){
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//        return new ResponseEntity<>(product, HttpStatus.OK);
//    }
//
//    @GetMapping("/products/brands")
//    public  List<String> ProductName(){
//        /*
//        Get all product name brands
//         */
//        return productRepository.findDistinctName();
//    }
//    // products
//    @PostMapping("/products")
//    public ResponseEntity<Product> CreateProduct(@RequestBody Product body){
//        /*
//         Create a new product
//         */
//        String productName = body.getName();
//        if(productRepository.existsProductByName(productName)){
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//        String code = UUID.randomUUID().toString();
//        productRepository.save(body);
//        return new ResponseEntity<>(body, HttpStatus.CREATED);
//    }
//
//    // /products/<long:id>
//    @DeleteMapping("/products/{id}")
//    public ResponseEntity DeleteProduct(@PathVariable Long id){
//        /*
//         Delete product by id
//         */
//        Optional<Product> product = productRepository.findById(id);
//        if(product.isPresent()){
//            productRepository.deleteById(id);
//            return new ResponseEntity(HttpStatus.OK);
//        }
//        return new ResponseEntity(HttpStatus.BAD_REQUEST);
//    }

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/home")
    public String home(){
        return "<h1> Welcome to Product service </h1>";
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable long id) {
        return productService.getProductById(id);
    }

    @PostMapping
    public Product addProduct(@RequestBody Product product) {
        return productService.addProduct(product);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable long id, @RequestBody Product product) {
        return productService.updateProduct(id, product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable long id) {
        productService.deleteProduct(id);
    }

    // Rating update route
    @PutMapping("/update-rating/{id}")
    public Product updateRating(@PathVariable long id, @RequestBody double rating){
        return productService.updateRating(id, rating);
    }
}

