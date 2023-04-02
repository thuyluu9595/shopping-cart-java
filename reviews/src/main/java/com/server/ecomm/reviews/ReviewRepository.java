package com.server.ecomm.reviews;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByProductIdAndUserId(long productId, long userId);
    List<Review> findByUserId(long userId);
    List<Review> findByProductId(long productId);
}
