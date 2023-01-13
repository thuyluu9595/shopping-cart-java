package com.example.order;

import com.example.order.models.Order;
import com.example.order.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;


    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id){
        return orderRepository.findById(id).orElse(null);
    }

    public Order createOrder(Order order){
        return orderRepository.save(order);
    }

    public Order updateOrder(Long id, Order order){
        Order existingOrder = orderRepository.findById(id).orElse(null);

        if (existingOrder == null) return null;

        order.setId(id);
        return orderRepository.save(order);

    }


}
