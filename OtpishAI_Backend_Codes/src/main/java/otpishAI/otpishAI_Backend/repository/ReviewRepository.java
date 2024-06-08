package otpishAI.otpishAI_Backend.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import otpishAI.otpishAI_Backend.entity.Review;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
   List<Review> findAllByProductNumOrderByReviewNum(Long productNum);

   @Transactional
   @Modifying(clearAutomatically = true)
   void deleteByReviewNum(Long productNum);

}