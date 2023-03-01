package com.server.ecomm.order;

import com.server.ecomm.order.models.Order;
import com.server.ecomm.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
     * @param order order object
     * @return created order
     */
    public Order createOrder(Order order){

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

//    /**
//     * Return number of distinct users
//     * @return : number of distinct user
//     */
//    public long countDistinctUsers(){
//        return orderRepository.countDistinctByUserId();
//    }
//
//    /**
//     * Return total number of orders
//     * @return : number of orders
//     */
//    public long countAllOrders(){
//        return orderRepository.count();
//    }
//
//    /**
//     * Return the total sale
//     * @return : total sale
//     */
//    public double totalSale(){
//        List<Order> orderList = getAllOrders();
//        double sumSale = 0;
//        for (Order order : orderList){
//            sumSale += order.getTotalPrice();
//        }
//        return sumSale;
//    }

    /**
     * get all orders by user id
     * @param userId : user id
     * @return list of orders
     */
    public List<Order> getOrdersByUser(Long userId){
        return orderRepository.findAllByUserId(userId);
    }

    /**
     * Updating delivery status and delivery time
     * @param orderId : order id
     * @return updated order
     */
    public Order updateDeliveryStatus(Long orderId){
        Order order = getOrderById(orderId);
        if (order == null) return null;
        order.setDelivered(true);
        order.setDeliveredAt(LocalDateTime.now());
        orderRepository.save(order);
        return order;
    }

    /**
     * Making a cancel request for an order
     * @param id : order id
     * @return order
     */
    public Order cancelOrderRequest(Long id){
        Order order = getOrderById(id);
        if (order == null || order.isDelivered()) return null;

        order.setRequestCancel(true);
        order.setRequestedAt(LocalDateTime.now());
        orderRepository.save(order);
        return order;
    }

    /**
     * Confirming canceling request for order
     * @param id : order id
     * @return order
     */
    public Order confirmCancelRequest(Long id){
        Order order = getOrderById(id);
        if (order == null) return null;

        order.setCancelled(true);
        order.setCancelledAt(LocalDateTime.now());
        orderRepository.save(order);
        return order;
    }
}
