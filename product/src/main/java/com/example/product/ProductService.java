package com.example.product;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(long id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(long id, Product product) {
        Product existingProduct = productRepository.findById(id).orElse(null);
        if (existingProduct == null) {
            return null;
        }
        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setQty(product.getQty());
        return productRepository.save(existingProduct);
    }

    public void deleteProduct(long id) {
        productRepository.deleteById(id);
    }

    public Product updateRating(Long id, double rating){
        Product product = productRepository.findById(id).orElse(null);
        if(product == null){
            return null;
        }
        if(product.getReview_count() == 0){
            product.setRating(rating);
        } else {
            double new_rating = Math.round(product.getRating() * product.getReview_count()*1.0 + rating) / (product.getReview_count() + 1.0);
            product.setRating(new_rating);
        }
        product.setReview_count(product.getReview_count()+1);
        productRepository.save(product);
        return product;
    }
}

