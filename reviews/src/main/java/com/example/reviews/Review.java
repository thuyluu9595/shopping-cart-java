package com.example.reviews;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name="review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private long user_id;

    @Column(nullable = false, unique = true)
    private long product_id;

    private String comment;

    private double rating;

    public Review() {
    }

    public Review(long user_id, long product_id, String comment, double rating) {
        this.user_id = user_id;
        this.product_id = product_id;
        this.comment = comment;
        this.rating = rating;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(long product_id) {
        this.product_id = product_id;
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

    public void setRating(float rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id='" + id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", product_id='" + product_id + '\'' +
                ", comment='" + comment + '\'' +
                ", rating=" + rating +
                '}';
    }
}
