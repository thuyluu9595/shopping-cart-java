package com.example.reviews;

import jakarta.persistence.*;

@Entity
@Table(name="review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long productId;

    private String comment;

    private double rating;

    public Review() {
    }

    public Review(long userId, long productId, String comment, double rating) {
        this.userId = userId;
        this.productId = productId;
        this.comment = comment;
        this.rating = rating;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long user_id) {
        this.userId = user_id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long product_id) {
        this.productId = product_id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id='" + id + '\'' +
                ", user_id='" + userId + '\'' +
                ", product_id='" + productId + '\'' +
                ", comment='" + comment + '\'' +
                ", rating=" + rating +
                '}';
    }
}
