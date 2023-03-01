package com.server.ecomm.reviews;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name= "product-service", url = "localhost:8081")
public interface ProductServiceProxy {
    @PutMapping("/api/products/update-rating/{id}")
    void updateRating(@PathVariable Long id,
                      @RequestBody Review review);
}
