package com.example.reviews;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;


    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public List<Review> getAllReviews(){
        return reviewRepository.findAll();
    }

    public Review getReviewById(Long id){
        return reviewRepository.findById(id).orElse(null);
    }

    public Review addReview(Long product_id, Long user_id, Review review){
        review.setProduct_id(product_id);
        review.setUser_id(user_id);
        return reviewRepository.save(review);
    }

    public Review updateReview(Long id, Review review){
        Review exist_review = getReviewById(id);
        if (exist_review == null){
            return null;
        }
        exist_review.setComment(review.getComment());
        exist_review.setRating(review.getRating());
        return reviewRepository.save(exist_review);
    }

    public boolean deleteReview(Long id){
        try {
            reviewRepository.deleteById(id);
            return true;
        } catch (EntityNotFoundException e){
            return false;
        }

    }
}
