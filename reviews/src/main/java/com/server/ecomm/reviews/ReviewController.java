package com.server.ecomm.reviews;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    private final ProductServiceProxy productServiceProxy;

    @Autowired
    public ReviewController(ReviewService reviewService, ProductServiceProxy productServiceProxy) {
        this.reviewService = reviewService;
        this.productServiceProxy = productServiceProxy;
    }


    @GetMapping("/home")
    public String home(){
        return "<h1> Welcome to Review Route </h1>";
    }

    @GetMapping("/test")
    public String test(){
        Long product_id;
        product_id = 1L;
        Long user_id;
        user_id = 1L;
        if(reviewService.isProductAndUserExist(product_id,user_id)){
            return "<h1> True</h1>";
        }
        return "<h1> False</h1>";
    }

    @GetMapping
    public List<Review> getAllReviews(){
        return reviewService.getAllReviews();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Review> getReviewById(@PathVariable Long id){
        Review review = reviewService.getReviewById(id);
        if(review == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(review, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Review> addReview(@RequestBody Review review, @RequestParam(value = "product_id") Long product_id,
                                            @RequestParam(value = "user_id") Long user_id){
        // Check if there exists review from the same person for the same product
        if(reviewService.isProductAndUserExist(product_id,user_id)) return new ResponseEntity<>(HttpStatus.CONFLICT);

        Review created_review = reviewService.addReview(product_id, user_id, review);

        if(created_review == null){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
//        try {
//            restTemplate.put("http://localhost:8081/api/products/update-rating/" + product_id,review);
//        } catch (Exception e){
//            System.out.println(e);
//        }
        try {
            productServiceProxy.updateRating(product_id, review);
        } catch (Exception e){
            System.out.println(e);
        }
        return new ResponseEntity<>(review, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Review> updateReview(@PathVariable Long id, @RequestBody Review review){

        Review updated_review = reviewService.updateReview(id, review);
        if(updated_review == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updated_review, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Review> deleteReview(@PathVariable Long id){
        boolean response = reviewService.deleteReview(id);
        if(!response){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Get list of reviews by user id
     * @param id : user id
     * @return List of Reviews
     */
    @GetMapping("/user/{id}")
    public List<Review> getReviewsByUserId(@PathVariable Long id){
        return reviewService.getReviewByUserId(id);
    }

    /**
     * Get list of reviews by product id
     * @param id : product id
     * @return List of Reviews
     */
    @GetMapping("/product/{id}")
    public List<Review> getReviewsByProductId(@PathVariable Long id){
        return reviewService.getReviewByProductId(id);
    }
}
