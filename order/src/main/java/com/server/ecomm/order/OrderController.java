package com.server.ecomm.order;

import com.server.ecomm.order.models.Order;
import com.server.ecomm.order.proxies.ProductServiceProxy;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@Slf4j
public class OrderController {

    private final OrderService orderService;

    private final ProductServiceProxy productServiceProxy;

    @Autowired
    public OrderController(OrderService orderService, ProductServiceProxy productServiceProxy) {
        this.orderService = orderService;
        this.productServiceProxy = productServiceProxy;
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
    @CircuitBreaker(name="default", fallbackMethod = "createOrderFallback")
    public ResponseEntity<Order> createOrder(@RequestBody Order order){
//        HttpEntity<List<Item>> requestBody = new HttpEntity<>(order.getOrderItems());
//
//        String DECREASING_PRODUCT_QTY_URL = "http://localhost:8081/api/products/decrease-qty";
//        try {
//            restTemplate.exchange(DECREASING_PRODUCT_QTY_URL, HttpMethod.PUT, requestBody, Void.class);
//        } catch (Exception e){
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
        productServiceProxy.decreasingProductQty(order.getOrderItems());

        Order created_order = orderService.createOrder(order);

        if (created_order == null){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(created_order, HttpStatus.CREATED);
    }

    public ResponseEntity<?> createOrderFallback(Order order, Exception e){
        log.error(e.toString());
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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

//        HttpEntity<List<Item>> requestBody = new HttpEntity<>(order.getOrderItems());
//
//        String INCREASING_PRODUCT_QTY_URL = "http://localhost:8081/api/products/increase-qty";
//        restTemplate.exchange(INCREASING_PRODUCT_QTY_URL, HttpMethod.PUT, requestBody, Void.class);

        productServiceProxy.increasingProductQty(order.getOrderItems());
        if (orderService.confirmCancelRequest(id) == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

/*
POST http://localhost:8083/api/orders
Content-Type: application/json

{
  "orderItems": [
    {
      "productId": 1,
      "name": "Nike",
      "price": 5.5,
      "qty": 2
    },
    {
      "productId": 2,
      "name": "Adidas",
      "price": 4.5,
      "qty": 1
    }
  ],
  "address": {
    "name": "Thuy Luu",
    "address": "4338 Houndsbrook Way",
    "city": "San Jose",
    "zipCode": "95111",
    "country": "USA"
  },
  "paymentMethod": "PAYPAL",
  "paymentResult": {
    "status": "non",
    "updateTime": "01/19/23",
    "emailAddress": "thuyluu9595@gmail.com"
  },
  "shippingPrice": 2.0,
  "taxPrice": 1.0,
  "itemPrice": 10.0,
  "totalPrice": 13.0,
  "userId": 1,
  "isDelivered": false,
  "requestCancel": false,
  "isCancelled": false
}
 */