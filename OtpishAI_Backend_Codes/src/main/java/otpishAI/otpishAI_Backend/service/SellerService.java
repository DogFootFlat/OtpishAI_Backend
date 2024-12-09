
package otpishAI.otpishAI_Backend.service;

import lombok.AllArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import otpishAI.otpishAI_Backend.dto.SellerDTO;
import otpishAI.otpishAI_Backend.entity.Seller;
import otpishAI.otpishAI_Backend.repository.SellerRepository;
import otpishAI.otpishAI_Backend.jwt.JWTUtil;
import otpishAI.otpishAI_Backend.repository.TokenrefreshRepository;
import otpishAI.otpishAI_Backend.service.CookieService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@AllArgsConstructor
public class SellerService {

    private final String IMAGE_UPLOAD_DIR = "seller/img/"; // 이미지 저장 폴더 경로 상수 추가
    private final SellerRepository sellerRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;
    private final CookieService cookieService;
    private final TokenrefreshRepository tokenrefreshRepository;

    public boolean registerSeller(SellerDTO sellerDto) {
        Seller isExist = sellerRepository.findBySellerId(sellerDto.getSellerId());
        if (isExist == null) {
            return false;
        }
        else{
        Seller seller = new Seller();
        seller.setSellerId(sellerDto.getSellerId());
        seller.setSellerEmail(sellerDto.getSellerEmail());
        seller.setSellerPhone(sellerDto.getSellerPhone());
        seller.setSellerAddr(sellerDto.getSellerAddr());
        seller.setSellerName(sellerDto.getSellerName());
        seller.setSellerBrandName(sellerDto.getSellerBrandName());
        seller.setSellerImg(sellerDto.getSellerImg());
        seller.setSellerPwd(passwordEncoder.encode(sellerDto.getSellerPwd()));
        seller.setRole("ROLE_SELLER");
        sellerRepository.save(seller);

        return true;
        }
    }

    public boolean loginSeller(SellerDTO sellerDto, HttpServletResponse response) {
        Seller seller = sellerRepository.findBySellerId(sellerDto.getSellerId());
        if (seller != null && passwordEncoder.matches(sellerDto.getSellerPwd(), seller.getSellerPwd())) {
            // JWT 생성
            String accessToken = jwtUtil.createJwt("access", seller.getSellerId(), "ROLE_SELLER", 600000L);
            String refreshToken = jwtUtil.createJwt("refresh", seller.getSellerId(), "ROLE_SELLER", 86400000L);

            // 리프레시 토큰 저장 DB에 기존의 리프레시 토큰 삭제 후 새 리프레시 토큰 저장
            tokenrefreshRepository.deleteByUsername(seller.getSellerId());
            cookieService.addRefreshEntity(seller.getSellerId(), refreshToken, 86400000L);

            // 쿠키에 access 토큰 추가
            response.addCookie(cookieService.createCookie("access", accessToken));

            return true;
        }
        return false;
    }

    public SellerDTO getSellerProfile(String sellerId) {
        Seller seller = sellerRepository.findBySellerId(sellerId);
        if (seller == null) {
            throw new RuntimeException("Seller not found");
        }
        return new SellerDTO(seller);
    }


    public boolean updateSeller(SellerDTO sellerDto) {
        Seller seller = sellerRepository.findBySellerId(sellerDto.getSellerId());
        if (seller == null) {
            seller.setSellerEmail(sellerDto.getSellerEmail());
            seller.setSellerPhone(sellerDto.getSellerPhone());
            seller.setSellerAddr(sellerDto.getSellerAddr());
            seller.setSellerName(sellerDto.getSellerName());
            seller.setSellerBrandName(sellerDto.getSellerBrandName());
            seller.setSellerImg(sellerDto.getSellerImg());
            seller.setSellerPwd(passwordEncoder.encode(sellerDto.getSellerPwd()));
            sellerRepository.save(seller);
            return true;
        }
        else{
            return false;
        }
    }


    public String saveImage(MultipartFile sellerImg) throws IOException {
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
            String fileName = System.currentTimeMillis() + "_" + sellerImg.getOriginalFilename();
            String filePath = IMAGE_UPLOAD_DIR + fileName;

            // 이미지 파일 저장
            Files.copy(sellerImg.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);

            return filePath; // 저장된 파일 경로 반환
        } catch (IOException e) {
            // 오류 로그 출력 및 예외 처리
            System.err.println("Error saving image: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

}
