package otpishAI.otpishAI_Backend.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
import otpishAI.otpishAI_Backend.jwt.JWTUtil;
import otpishAI.otpishAI_Backend.service.CustomersService;
import otpishAI.otpishAI_Backend.service.ProductService;
import otpishAI.otpishAI_Backend.service.RefreshTCheckService;
import otpishAI.otpishAI_Backend.service.ReviewService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@AllArgsConstructor
public class ReviewController {


    private final ReviewService reviewService;
    private final RefreshTCheckService refreshTCheckService;
    private final JWTUtil jwtUtil;
    private final CustomersService customersService;
    @PostMapping("/review")
    public ResponseEntity<?> addReview(@RequestBody ReviewDTO reviewDTO, HttpServletRequest request, HttpServletResponse response){
        String access = refreshTCheckService.getAccessT(request, response);

        String username = jwtUtil.getUsername(access);

        String email = customersService.responseUser(username).getEmail();
        if(reviewService.saveReview(reviewDTO, email))
        {
            System.out.println("Review Add Success");
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/review/{reviewNum}")
    public ResponseEntity<?> deleteReview(@PathVariable("reviewNum") Long reviewNum, HttpServletRequest request, HttpServletResponse response)
    {
        String access = refreshTCheckService.getAccessT(request, response);

        String username = jwtUtil.getUsername(access);

        String email = customersService.responseUser(username).getEmail();
        if(reviewService.reviewOwnerCheck(reviewNum, email)){
            if(reviewService.deleteReview(reviewNum, email)){
                return new ResponseEntity<>(HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        else{
            System.out.println("Invalid Email");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/review/{reviewNum}")
    public ResponseEntity<?> updateReview(@PathVariable("reviewNum") Long reviewNum, @RequestBody ReviewDTO reviewDTO, HttpServletRequest request, HttpServletResponse response)
    {
        String access = refreshTCheckService.getAccessT(request, response);

        String username = jwtUtil.getUsername(access);

        String email = customersService.responseUser(username).getEmail();
        if(reviewService.reviewOwnerCheck(reviewNum, email)){
            if(reviewService.updateReview(reviewDTO, email, reviewNum)){
                return new ResponseEntity<>(HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        else{
            System.out.println("Invalid Email");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
