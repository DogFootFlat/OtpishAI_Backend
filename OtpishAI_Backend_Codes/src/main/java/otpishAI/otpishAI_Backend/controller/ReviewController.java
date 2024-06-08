package otpishAI.otpishAI_Backend.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import otpishAI.otpishAI_Backend.dto.ProductDetailDTO;
import otpishAI.otpishAI_Backend.dto.ReviewDTO;
import otpishAI.otpishAI_Backend.entity.Product;
import otpishAI.otpishAI_Backend.entity.ProductDetail;
import otpishAI.otpishAI_Backend.entity.Review;
import otpishAI.otpishAI_Backend.service.ProductService;
import otpishAI.otpishAI_Backend.service.ReviewService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@AllArgsConstructor
public class ReviewController {


    private final ReviewService reviewService;

    @PostMapping("/review")
    public ResponseEntity<?> addReview(@RequestBody ReviewDTO reviewDTO){
        reviewDTO.setReviewRdate(LocalDateTime.now());
        reviewService.saveReview(reviewDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/review/{reviewNum}")
    public ResponseEntity<?> deleteReview(@PathVariable("reviewNum") Long reviewNum)
    {
        reviewService.deleteReview(reviewNum);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
