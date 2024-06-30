package otpishAI.otpishAI_Backend.service;

import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import otpishAI.otpishAI_Backend.dto.ReviewDTO;
import otpishAI.otpishAI_Backend.entity.Review;
import otpishAI.otpishAI_Backend.repository.ReviewRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public List<Review> reviewsByProductNum(Long productNum){

        return reviewRepository.findAllByProductNumOrderByReviewNum(productNum);
    }

    public Boolean saveReview(ReviewDTO reviewDTO, String email){
        try {
            Review review = new Review();
            review.setProductNum(reviewDTO.getProductNum());
            review.setReviewImg(reviewDTO.getReviewImg());
            review.setReviewOwner(email);
            review.setReviewTitle(reviewDTO.getReviewTitle());
            review.setReviewContent(reviewDTO.getReviewContent());
            review.setReviewRdate(LocalDateTime.now());

            reviewRepository.save(review);
            return true;
        } catch (DataAccessException e) {
            System.out.println("Error Log : " + e);
            return false;
        }
    }

    public Boolean deleteReview(Long reviewNum, String email){
        try{
            reviewRepository.deleteByReviewNum(reviewNum);
            return true;
        }catch (DataAccessException e){
            return false;
        }

    }

    public Boolean reviewOwnerCheck(Long reviewNum, String email){
        try{
           Review review = reviewRepository.findByReviewNum(reviewNum);
            if(review.getReviewOwner().equals(email)){
                return true;
            }
            else{
                return false;
            }
        } catch (DataAccessException e){
            return false;
        }

    }

}
