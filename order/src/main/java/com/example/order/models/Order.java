package com.example.order.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true) // fetch = FetchType.LAZY, mappedBy = "order",
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private List<Item> orderItems;

    @Embedded
    private ShippingAddress address;

    private PaymentMethod paymentMethod;

    @Embedded
    private PaymentResult paymentResult;

    private double shippingPrice;

    private double taxPrice;

    private double itemPrice;

    private double totalPrice;

    private Long userId;

    private boolean isDelivered;

    private LocalDateTime deliveredAt;

    private boolean requestCancel;

    private LocalDateTime requestedAt;

    private String reasonCancel;

    private boolean isCancelled;

    private LocalDateTime cancelledAt;

    private LocalDateTime timestamp;

    public Order(List<Item> orderItems, ShippingAddress address, PaymentMethod paymentMethod, PaymentResult paymentResult,
                 double shippingPrice, double taxPrice, double itemPrice, double totalPrice, Long userId, boolean isDelivered,
                 LocalDateTime deliveredAt, boolean requestCancel, LocalDateTime requestedAt, String reasonCancel,
                 boolean isCancelled, LocalDateTime cancelledAt, LocalDateTime timestamp) {
        this.orderItems = orderItems;
        this.address = address;
        this.paymentMethod = paymentMethod;
        this.paymentResult = paymentResult;
        this.shippingPrice = shippingPrice;
        this.taxPrice = taxPrice;
        this.itemPrice = itemPrice;
        this.totalPrice = totalPrice;
        this.userId = userId;
        this.isDelivered = isDelivered;
        this.deliveredAt = deliveredAt;
        this.requestCancel = requestCancel;
        this.requestedAt = requestedAt;
        this.reasonCancel = reasonCancel;
        this.isCancelled = isCancelled;
        this.cancelledAt = cancelledAt;
        this.timestamp = timestamp;
    }

    public Order() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Item> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<Item> orderItems) {
        this.orderItems = orderItems;
    }

    public ShippingAddress getAddress() {
        return address;
    }

    public void setAddress(ShippingAddress address) {
        this.address = address;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public PaymentResult getPaymentResult() {
        return paymentResult;
    }

    public void setPaymentResult(PaymentResult paymentResult) {
        this.paymentResult = paymentResult;
    }

    public double getShippingPrice() {
        return shippingPrice;
    }

    public void setShippingPrice(double shippingPrice) {
        this.shippingPrice = shippingPrice;
    }

    public double getTaxPrice() {
        return taxPrice;
    }

    public void setTaxPrice(double taxPrice) {
        this.taxPrice = taxPrice;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public boolean isDelivered() {
        return isDelivered;
    }

    public void setDelivered(boolean delivered) {
        isDelivered = delivered;
    }

    public LocalDateTime getDeliveredAt() {
        return deliveredAt;
    }

    public void setDeliveredAt(LocalDateTime deliveredAt) {
        this.deliveredAt = deliveredAt;
    }

    public boolean isRequestCancel() {
        return requestCancel;
    }

    public void setRequestCancel(boolean requestCancel) {
        this.requestCancel = requestCancel;
    }

    public LocalDateTime getRequestedAt() {
        return requestedAt;
    }

    public void setRequestedAt(LocalDateTime requestedAt) {
        this.requestedAt = requestedAt;
    }

    public String getReasonCancel() {
        return reasonCancel;
    }

    public void setReasonCancel(String reasonCancel) {
        this.reasonCancel = reasonCancel;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }

    public LocalDateTime getCancelledAt() {
        return cancelledAt;
    }

    public void setCancelledAt(LocalDateTime cancelledAt) {
        this.cancelledAt = cancelledAt;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderItems=" + orderItems +
                ", address=" + address +
                ", paymentMethod=" + paymentMethod +
                ", paymentResult=" + paymentResult +
                ", shippingPrice=" + shippingPrice +
                ", taxPrice=" + taxPrice +
                ", itemPrice=" + itemPrice +
                ", totalPrice=" + totalPrice +
                ", userId=" + userId +
                ", isDelivered=" + isDelivered +
                ", deliveredAt=" + deliveredAt +
                ", requestCancel=" + requestCancel +
                ", requestedAt=" + requestedAt +
                ", reasonCancel='" + reasonCancel + '\'' +
                ", isCancelled=" + isCancelled +
                ", cancelledAt=" + cancelledAt +
                ", timestamp=" + timestamp +
                '}';
    }
}
