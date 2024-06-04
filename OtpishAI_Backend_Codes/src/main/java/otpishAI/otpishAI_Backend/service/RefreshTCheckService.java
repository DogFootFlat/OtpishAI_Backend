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
        String refresh = null;
        //리프레시 토큰 null 검증
        if (access.equals("")) {
            return "";
        }
        Optional<Tokenrefresh> tokenrefresh = tokenrefreshRepository.findByUsername(jwtUtil.getUsername(access));
        if(tokenrefresh.isPresent()){
            try {
                refresh = tokenrefresh.get().getRefresh();
                jwtUtil.isExpired(refresh);
            } catch (ExpiredJwtException e) {
                //만료시 예외 처리
                return  "";
            }
        }

        return refresh;
    }
    
    //리프레시 토큰 가져오는 함수
    public String getAccessT(HttpServletRequest request, HttpServletResponse response){
        String access = null;
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return "";
        }
        else {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("access")) {
                    access = cookie.getValue();
                    return access;
                }
            }
            return "";
        }
    }
}
