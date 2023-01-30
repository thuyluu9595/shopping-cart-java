package com.example.order.proxies;

import com.example.order.models.Item;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "product-service", url = "localhost:8081")
public interface ProductServiceProxy {
    @PutMapping("/api/products/decrease-qty")
    void decreasingProductQty(@RequestBody List<Item> Items);

    @PutMapping("/api/products/increase-qty")
    void increasingProductQty(@RequestBody List<Item> Items);
}
