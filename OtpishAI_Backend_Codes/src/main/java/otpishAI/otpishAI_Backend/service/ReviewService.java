package otpishAI.otpishAI_Backend.service;

import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import otpishAI.otpishAI_Backend.dto.ReviewDTO;
import otpishAI.otpishAI_Backend.entity.Product;
import otpishAI.otpishAI_Backend.entity.Review;
import otpishAI.otpishAI_Backend.repository.ProductRepository;
import otpishAI.otpishAI_Backend.repository.ReviewRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;

    private final String IMAGE_UPLOAD_DIR = "reviews/img/"; // 이미지 저장 폴더 경로 상수 추가
    public List<Review> reviewsByProductNum(Long productNum){

        return reviewRepository.findAllByProductNumOrderByReviewNum(productNum);
    }

    public List<ReviewDTO> reviewsByReviewOwner(String reviewOwner){
        List<Review> reviews = reviewRepository.findAllByReviewOwner(reviewOwner);
        List<ReviewDTO> reviewDTOS = reviews.stream().map(ReviewDTO::new).collect(Collectors.toList());

        return reviewDTOS;
    }
    public Boolean saveReview(ReviewDTO reviewDTO, String email){
        try {
            Product product = productRepository.findByProductNum(reviewDTO.getProductNum());
            if(product != null)
            {
                product.setReview(product.getReview() + 1L);
                productRepository.save(product);
            }
            else{
                return false;
            }
            Review review;
            review = setReview(reviewDTO);
            review.setReviewOwner(email);

            reviewRepository.save(review);

            return true;
        } catch (DataAccessException e) {
            System.out.println("Error Log : " + e);
            return false;
        }
    }
    public String saveImage(MultipartFile reviewimg) throws IOException {
        try {
            // 이미지 저장 폴더 생성
            File uploadDir = new File(IMAGE_UPLOAD_DIR);
            if (!uploadDir.exists()) {
                if (!uploadDir.mkdirs()) {
                    throw new IOException("Could not create directory: " + IMAGE_UPLOAD_DIR);
                }
            }

        // 쓰기 권한 확인
            if (!uploadDir.canWrite()) {
                throw new IOException("No write permission for directory: " + IMAGE_UPLOAD_DIR);
            }

            // 파일 경로 설정
            String fileName = System.currentTimeMillis() + "_" + reviewimg.getOriginalFilename();
            String filePath = IMAGE_UPLOAD_DIR + fileName;

            // 이미지 파일 저장
            Files.copy(reviewimg.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);

            return filePath; // 저장된 파일 경로 반환
        } catch (IOException e) {
            // 오류 로그 출력 및 예외 처리
            System.err.println("Error saving image: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public Boolean updateReview(ReviewDTO reviewDTO, String email, Long reviewNum){
        Review isExist = reviewRepository.findByReviewNum(reviewNum);
        if(isExist != null) {
            try {
                Review review;
                review = setReview(reviewDTO);
                review.setReviewNum(isExist.getReviewNum());
                review.setReviewOwner(email);
                reviewRepository.save(review);
                return true;
            } catch (DataAccessException e) {
                System.out.println("Error Log : " + e);
                return false;
            }
        }
        else{
            return false;
        }
    }

    public Boolean deleteReview(Long reviewNum, String email){
        try{
            Review review = reviewRepository.findByReviewNum(reviewNum);
            if( review != null && review.getReviewOwner().equals(email)){
                Product product = productRepository.findByProductNum(review.getProductNum());
                if(product != null)
                {
                    product.setReview(product.getReview() - 1L);
                    productRepository.save(product);
                }
                else{
                    return false;
                }

            }
            else{
                return false;
            }
            reviewRepository.deleteByReviewNum(reviewNum);
            return true;
        }catch (DataAccessException e){
            e.printStackTrace();
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
            e.printStackTrace();
            return false;
        }

    }

    private Review setReview(ReviewDTO reviewDTO){
        Review review = new Review();
        review.setReviewImg(reviewDTO.getReviewImg());
        review.setReviewTitle(reviewDTO.getReviewTitle());
        review.setReviewContent(reviewDTO.getReviewContent());
        review.setReviewRdate(LocalDateTime.now());
        return review;
    }
}
