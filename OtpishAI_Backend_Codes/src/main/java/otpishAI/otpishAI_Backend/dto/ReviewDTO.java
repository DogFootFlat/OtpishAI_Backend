package otpishAI.otpishAI_Backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import otpishAI.otpishAI_Backend.entity.Review;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {

    private Long reviewNum;

    private String reviewOwner;

    private Long productNum;

    private String reviewTitle;

    private String reviewContent;

    private String[] reviewImg = new String[0];

    private LocalDateTime reviewRdate;

    public ReviewDTO(Review review){
        reviewNum = review.getReviewNum();
        reviewOwner = review.getReviewOwner();
        productNum = review.getProductNum();
        reviewTitle = review.getReviewTitle();
        reviewContent = review.getReviewContent();
        reviewImg = review.getReviewImg();
        reviewRdate = review.getReviewRdate();
    }

}
