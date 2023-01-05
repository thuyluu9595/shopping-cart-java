package com.example.reviews;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }


    @GetMapping("/home")
    public String home(){
        return "<h1> Welcome to Review Route </h1>";
    }

//    @GetMapping("/create")
//    public ResponseEntity<String> create(){
//        Review review = new Review(123,456, "test review", 3.5);
//        reviewRepository.save(review);
//        return new ResponseEntity<>("created successfully", HttpStatus.OK);
//    }
}
