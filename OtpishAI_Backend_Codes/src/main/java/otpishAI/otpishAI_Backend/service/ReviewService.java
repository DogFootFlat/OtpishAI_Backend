package otpishAI.otpishAI_Backend.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import otpishAI.otpishAI_Backend.dto.ReviewDTO;
import otpishAI.otpishAI_Backend.entity.Review;
import otpishAI.otpishAI_Backend.repository.ReviewRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public List<Review> reviewsByProductNum(Long productNum){
        return reviewRepository.findAllByProductNumOrderByReviewNum(productNum);
    }

    public void saveReview(ReviewDTO reviewDTO){
        Review review = new Review();
        review.setProductNum(reviewDTO.getProductNum());
        review.setReviewImg(reviewDTO.getReviewImg());
        review.setReviewOwner(reviewDTO.getReviewOwner());
        review.setReviewTitle(reviewDTO.getReviewTitle());
        review.setReviewContent(reviewDTO.getReviewContent());
        review.setReviewRdate(reviewDTO.getReviewRdate());
        reviewRepository.save(review);
    }

    public void deleteReview(Long reviewNum){
        reviewRepository.deleteByReviewNum(reviewNum);
    }

}
