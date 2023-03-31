package com.server.ecomm.product;

import com.server.ecomm.product.models.OrderItem;
import com.server.ecomm.product.models.Product;
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

    public Product createProduct(Product product) {
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

    private static double round(double value){
        int scale = (int) Math.pow(10, 1);
        return (double) Math.round(value * scale) / scale;
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
            double new_rating = (product.getRating() * product.getReview_count()*1.0 + rating) / (product.getReview_count() + 1.0);
            new_rating = round(new_rating);
            product.setRating(new_rating);
        }
        product.setReview_count(product.getReview_count()+1);
        productRepository.save(product);
        return product;
    }

    public boolean decreaseProductQty(List<OrderItem> orderItems){
        for (OrderItem orderItem : orderItems) {
            Product product = getProductById(orderItem.getProductId());
            if (orderItem.getQty() > product.getQty()) return false;
        }
        for (OrderItem item : orderItems) {
            Product p = getProductById(item.getProductId());
            p.setQty(p.getQty() - item.getQty());
            productRepository.save(p);
        }
        return true;
    }

    public void increaseProductQty(List<OrderItem> orderItems){
        for (OrderItem item : orderItems) {
            Product product = getProductById(item.getProductId());
            product.setQty(product.getQty() + item.getQty());
            productRepository.save(product);
        }
    }
}

