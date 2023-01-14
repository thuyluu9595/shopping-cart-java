package com.example.order;

import com.example.order.models.Item;
import com.example.order.models.Order;
import com.example.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    /**
     * Get a list of all orders
     * @return List of orders
     */
    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }

    /**
     * Get order by id
     * @param id : order id
     * @return Order
     */
    public Order getOrderById(Long id){
        return orderRepository.findById(id).orElse(null);
    }

    /**
     * Creating a new order
     * @param order : order object
     * @return created order
     */
    public Order createOrder(Order order){
        for (Item item : order.getOrderItems()){
            item.setOrder(order);
        }
        return orderRepository.save(order);
    }

    /**
     * Updating an existed order
     * @param id : order id
     * @param order : updating order
     * @return updated order
     */
    public Order updateOrder(Long id, Order order){
        Order existingOrder = orderRepository.findById(id).orElse(null);

        if (existingOrder == null) return null;

        order.setId(id);
        return orderRepository.save(order);
    }

    /**
     * Deleting an order by id
     * @param id : order id
     * @return true if successfully deleted order, otherwise return false
     */
    public boolean deleteOrder(Long id){
        try {
            orderRepository.deleteById(id);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

}
