package com.example.order;

import com.example.order.models.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    /**
     * This route is used for testing purpose
     * @return String
     */
    @GetMapping("/home")
    public String home(){
        return "<h1>Welcome to Order Service</h1>";
    }

    @GetMapping("")
    public List<Order> getAllOrder(){
        return orderService.getAllOrders();
    }

    /**
     * Post request route to create a new order
     * @param order : new order
     * @return : new order
     */
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order){
        Order created_order = orderService.createOrder(order);
        if (created_order == null){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(created_order, HttpStatus.CREATED);
    }

    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody Order order){
        if(orderService.updateOrder(id, order) == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

}
