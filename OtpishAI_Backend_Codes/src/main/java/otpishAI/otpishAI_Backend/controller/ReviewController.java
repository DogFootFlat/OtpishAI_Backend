package otpishAI.otpishAI_Backend.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import otpishAI.otpishAI_Backend.dto.CartResponseDTO;
import otpishAI.otpishAI_Backend.dto.CustomersDTO;
import otpishAI.otpishAI_Backend.dto.ReviewDTO;
import otpishAI.otpishAI_Backend.jwt.JWTUtil;
import otpishAI.otpishAI_Backend.service.CustomersService;
import otpishAI.otpishAI_Backend.service.RefreshTCheckService;
import otpishAI.otpishAI_Backend.service.ReviewService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final RefreshTCheckService refreshTCheckService;
    private final JWTUtil jwtUtil;
    private final CustomersService customersService;

    @PostMapping("/review")
    public ResponseEntity<?> addReview(
            @RequestBody ReviewDTO reviewDTO,
            @RequestParam(value = "reviewimg", required = false) MultipartFile[] reviewimgFiles,
            HttpServletRequest request,
            HttpServletResponse response) {

        String access = refreshTCheckService.getAccessT(request, response);
        String username = jwtUtil.getUsername(access);
        String email = customersService.responseUser(username).getEmail();

        if(!access.equals(""))
        {
            List<String> imagePaths = new ArrayList<>();
            try {
                // 이미지 파일이 있는 경우에만 처리
                if (reviewimgFiles != null && reviewimgFiles.length > 0) {
                    for (MultipartFile reviewimg : reviewimgFiles) {
                        try {
                            // 각 파일을 저장하고 반환된 경로를 리스트에 추가
                            String imagePath = reviewService.saveImage(reviewimg);
                            imagePaths.add(imagePath);
                        } catch (IOException e) {
                            e.printStackTrace();
                            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                        }
                    }
                }

                // ReviewDTO에 이미지 경로 리스트를 배열로 설정 (이미지가 없을 경우 빈 배열로 설정)
                reviewDTO.setReviewImg(imagePaths.toArray(new String[0]));

                if (reviewService.saveReview(reviewDTO, email)) {
                    return new ResponseEntity<>(HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping("/review/{reviewNum}")
    public ResponseEntity<?> deleteReview(@PathVariable("reviewNum") Long reviewNum, HttpServletRequest request, HttpServletResponse response) {
        String access = refreshTCheckService.getAccessT(request, response);
        String username = jwtUtil.getUsername(access);
        String email = customersService.responseUser(username).getEmail();

        if(!access.equals(""))
        {
            if (reviewService.reviewOwnerCheck(reviewNum, email)) {
                if (reviewService.deleteReview(reviewNum, email)) {
                    return new ResponseEntity<>(HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            } else {
                System.out.println("Invalid Email");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("/review/{reviewNum}")
    public ResponseEntity<?> updateReview(
            @PathVariable("reviewNum") Long reviewNum,
            @RequestBody ReviewDTO reviewDTO,
            @RequestParam(value = "reviewimg", required = false) MultipartFile[] reviewimgFiles,
            HttpServletRequest request,
            HttpServletResponse response) {

        String access = refreshTCheckService.getAccessT(request, response);
        String username = jwtUtil.getUsername(access);
        String email = customersService.responseUser(username).getEmail();

        if(!access.equals(""))
        {
            List<String> imagePaths = new ArrayList<>();
            try {
                // 이미지 파일이 있는 경우에만 처리
                if (reviewimgFiles != null && reviewimgFiles.length > 0) {
                    for (MultipartFile reviewimg : reviewimgFiles) {
                        try {
                            String imagePath = reviewService.saveImage(reviewimg);
                            imagePaths.add(imagePath);
                        } catch (IOException e) {
                            e.printStackTrace();
                            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                        }
                    }
                    reviewDTO.setReviewImg(imagePaths.toArray(new String[0])); // 새 이미지 경로로 설정
                }
                // 이미지를 전송하지 않은 경우, reviewDTO의 이미지 필드를 그대로 두어 기존 데이터를 유지

                // 리뷰 업데이트 처리
                if (reviewService.reviewOwnerCheck(reviewNum, email)) {
                    if (reviewService.updateReview(reviewDTO, email, reviewNum)) {
                        return new ResponseEntity<>(HttpStatus.OK);
                    } else {
                        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                    }
                } else {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
