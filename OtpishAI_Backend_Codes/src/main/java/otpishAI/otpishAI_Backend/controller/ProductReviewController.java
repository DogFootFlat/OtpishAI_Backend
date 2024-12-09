package otpishAI.otpishAI_Backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.transaction.annotation.Transactional;
import otpishAI.otpishAI_Backend.entity.Product;
import otpishAI.otpishAI_Backend.entity.Review;
import otpishAI.otpishAI_Backend.repository.ProductRepository;
import otpishAI.otpishAI_Backend.repository.ReviewRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ProductReviewController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @GetMapping("/api/judge")
    @Transactional
    public ResponseEntity<String> updatePositiveReviewCount() {
        System.out.println("judgeCalled");
        // 리뷰의 내용을 담고 있는 칼럼명은 review_content입니다.
        List<Review> reviews = reviewRepository.findAll();
        RestTemplate restTemplate = new RestTemplate();

        // 긍정 리뷰 수를 저장할 Map
        Map<Long, Long> positiveReviewCountMap = new HashMap<>();
        for (Review review : reviews) {
            positiveReviewCountMap.putIfAbsent(review.getProductNum(), 0L);
        }
        // 모든 리뷰를 순회하며 감정 분석 API 호출
        for (Review review : reviews) {
            String apiUrl = "https://5296d1utk0a19v-5000.proxy.runpod.net/predict";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // 요청 바디 설정
            String requestBody = String.format("{\"text\": \"%s\"}", review.getReviewContent());
            HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

            try {
                // API 호출
                ResponseEntity<Map<String, Object>> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, new ParameterizedTypeReference<Map<String, Object>>() {
                });
                if (response.getStatusCode() == HttpStatus.OK) {
                    Map<String, Object> responseBody = response.getBody();
                    Integer sentiment = responseBody != null && responseBody.containsKey("total_point") ? (Integer) responseBody.get("total_point") : null;

                    // 긍정 리뷰인 경우 해당 product_num에 대해 카운트 증가
                    if (sentiment >= 0) {
                        positiveReviewCountMap.put(review.getProductNum(),
                                positiveReviewCountMap.getOrDefault(review.getProductNum(), 0L) + 1);
                    }
                    System.out.println("개별 분석 결과 : " + review.getProductNum() + " :: " + sentiment);
                }
                System.out.println("상품 번호별 분석 결과 : " + positiveReviewCountMap.get(review.getProductNum()));
            } catch (Exception e) {
                // 예외 처리 (API 호출 실패 등)
                System.err.println("리뷰 분석 중 에러 발생 : " + e.getMessage());
            }
        }

        System.out.println("최종 분석 결과 : " + positiveReviewCountMap);
        // 긍정 리뷰 수를 각 Product 엔티티에 업데이트
        for (Map.Entry<Long, Long> entry : positiveReviewCountMap.entrySet()) {
            Long productNum = entry.getKey();
            Long positiveCount = entry.getValue();
            Long totalReviews = reviewRepository.countByProductNum(productNum);

            // 긍정적인 리뷰 퍼센티지 계산 (실수형 연산을 통해 정확한 값 계산)
            Long judgeValue = totalReviews > 0 ? Math.round(((double) positiveCount / totalReviews) * 100) : 0L;

            System.out.println("긍정적인 리뷰 퍼센테이지 : " + judgeValue);
            Product product = productRepository.findById(productNum)
                    .orElseThrow(() -> new RuntimeException("Product not found with productNum: " + productNum));
            product.setJudge(judgeValue);
            productRepository.save(product);
        }

        return ResponseEntity.ok("Product judge field updated successfully with positive review counts.");
    }
}