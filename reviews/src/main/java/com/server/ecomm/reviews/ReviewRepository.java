package com.server.ecomm.reviews;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByProductIdAndUserId(Long productId, Long userId);
    List<Review> findByUserId(Long userId);
    List<Review> findByProductId(Long productId);
}
