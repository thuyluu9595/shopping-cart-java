package com.server.ecomm.product;

import com.server.ecomm.product.models.OrderItem;
import com.server.ecomm.product.models.Product;
import com.server.ecomm.product.models.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
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
        return productService.createProduct(product);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable long id, @RequestBody Product product) {
        return productService.updateProduct(id, product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable long id) {
        productService.deleteProduct(id);
    }

    /**
     * Update rating route
     * @param id : product id
     * @param review : review object
     * @return Product
     */
    @PutMapping("/update-rating/{id}")
    public Product updateRating(@PathVariable long id,
                                @RequestBody Review review){
        return productService.updateRating(id, review.getRating());
    }

    /**
     * Decrease product qty
     * @param orderItems : List of item object
     * @return HTTP response
     */
    @PutMapping("/decrease-qty")
    public ResponseEntity<?> decreasingProductQty(@RequestBody List<OrderItem> orderItems){
        if (productService.decreaseProductQty(orderItems)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Increase product qty
     * @param orderItems : List of item object
     * @return HTTP response
     */
    @PutMapping("/increase-qty")
    public ResponseEntity<?> increasingProductQty(@RequestBody List<OrderItem> orderItems){
        productService.increaseProductQty(orderItems);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

