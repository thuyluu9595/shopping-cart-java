package com.example.reviews;

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

    public Review addReview(Review review){
        return reviewRepository.save(review);
    }

    public Review updateReview(Long id, Review review){
        Review exist_review = getReviewById(id);
        if (exist_review == null){
            return null;
        }
        exist_review.setComment(review.getComment());
        exist_review.setRating(review.getRating());
        return exist_review;
    }

    public void deleteReview(Long id){
        reviewRepository.deleteById(id);
    }
}
