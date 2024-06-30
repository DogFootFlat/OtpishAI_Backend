package otpishAI.otpishAI_Backend.service;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import otpishAI.otpishAI_Backend.entity.Tokenrefresh;
import otpishAI.otpishAI_Backend.jwt.JWTUtil;
import otpishAI.otpishAI_Backend.repository.TokenrefreshRepository;

import java.util.Optional;

//리프레시 토큰 확인 서비스
@Service
@AllArgsConstructor
public class RefreshTCheckService {
    private final JWTUtil jwtUtil;

    private final TokenrefreshRepository tokenrefreshRepository;

    //리프레시 토큰 확인 함수
    //올바른 리프레시 토큰이 존재한다면 200을, 없다면 400을 반환
    public String RefreshTCheck(HttpServletRequest request, HttpServletResponse response){
        String access = getAccessT(request, response);
        //리프레시 토큰 null 검증
        if (access.equals("") || access == null) {
            return "";
        }

        Optional<Tokenrefresh> tokenrefreshOpt = tokenrefreshRepository.findByUsername(jwtUtil.getUsername(access));
        if (tokenrefreshOpt.isPresent()) {
            String refresh = tokenrefreshOpt.get().getRefresh();
            try {
                jwtUtil.isExpired(refresh);
                return refresh;
            } catch (ExpiredJwtException e) {
                // 만료시 예외 처리
                return "";
            }
        } else {
            return "";
        }
    }
    
    //리프레시 토큰 가져오는 함수
    public String getAccessT(HttpServletRequest request, HttpServletResponse response){
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length == 0) {
            return "";
        }

        for (Cookie cookie : cookies) {
            if ("access".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return "";
    }
}
