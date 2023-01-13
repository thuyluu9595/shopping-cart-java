package com.example.order;

import com.example.order.models.Order;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    /**
     * This route is used for testing purpose
     * @return String
     */
    @GetMapping("/home")
    public String home(){
        return "<h1>Welcome to Order Service</h1>";
    }

//    public List<Order>

}
