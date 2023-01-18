package com.example.order;

import com.example.order.models.Item;
import com.example.order.models.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final RestTemplate restTemplate;

    @Autowired
    public OrderController(OrderService orderService, RestTemplate restTemplate) {
        this.orderService = orderService;
        this.restTemplate = restTemplate;
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
        HttpEntity<List<Item>> requestBody = new HttpEntity<>(order.getOrderItems());

        String DECREASING_PRODUCT_QTY_URL = "http://localhost:8081/api/products/decrease-qty";
        ResponseEntity<?> productServiceResponse = restTemplate.exchange(DECREASING_PRODUCT_QTY_URL, HttpMethod.PUT, requestBody, Void.class);

        if (productServiceResponse.getStatusCode() == HttpStatus.BAD_REQUEST) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Order created_order = orderService.createOrder(order);

        if (created_order == null){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(created_order, HttpStatus.CREATED);
    }

    /**
     * Updating order route for order id
     * @param id : order id
     * @param order : updating order
     * @return : Updated order
     */
    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody Order order){
        if(orderService.updateOrder(id, order) == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    /**
     * Deleting order route
     * @param id : order id
     * @return HTTP Response
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long id){
        if(orderService.deleteOrder(id)){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Get orders by user id
     * @param id user id
     * @return list of orders
     */
    @GetMapping("/mine/{id}")
    public ResponseEntity<List<Order>> getOrdersByUserId(@PathVariable Long id){
        return new ResponseEntity<>(orderService.getOrdersByUser(id), HttpStatus.OK);
    }

    /**
     * Updating delivery status
     * @param id order id
     * @return HTTP response
     */
    @PutMapping("/{id}/deliver")
    public ResponseEntity<Order> updateDeliveryStatus(@PathVariable Long id){
        if(orderService.updateDeliveryStatus(id) == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Making a request for cancelling order
     * @param id : order id
     * @return HTTP response
     */
    @PutMapping("/{id}/cancel-request")
    public ResponseEntity<Order> cancelOrderRequest(@PathVariable Long id){
        if(orderService.cancelOrderRequest(id) == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Confirming cancel request for order
     * @param id : order id
     * @return HTTP response
     */
    @PutMapping("/{id}/cancelled")
    public ResponseEntity<?> confirmingCancelRequest(@PathVariable Long id){
        Order order = orderService.getOrderById(id);

        HttpEntity<List<Item>> requestBody = new HttpEntity<>(order.getOrderItems());

        String INCREASING_PRODUCT_QTY_URL = "http://localhost:8081/api/products/increase-qty";
        restTemplate.exchange(INCREASING_PRODUCT_QTY_URL, HttpMethod.PUT, requestBody, Void.class);

        if (orderService.confirmCancelRequest(id) == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
