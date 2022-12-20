package com.example.reviews;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReviewController {
    @GetMapping("/")
    public String home(){
        return "<h1> Welcome to Review Route </h1>";
    }
}
