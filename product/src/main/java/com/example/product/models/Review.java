package com.example.product.models;

public class Review {

    private Long user_id;

    private Long product_id;

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

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Long product_id) {
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

    public void setRating(double rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Review{" +
                ", user_id='" + user_id + '\'' +
                ", product_id='" + product_id + '\'' +
                ", comment='" + comment + '\'' +
                ", rating=" + rating +
                '}';
    }
}
