package otpishAI.otpishAI_Backend.jwt;

import lombok.AllArgsConstructor;
import otpishAI.otpishAI_Backend.repository.TokenrefreshRepository;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.GenericFilterBean;
import otpishAI.otpishAI_Backend.service.CookieService;

import java.io.IOException;

//post로 로그아웃 요청이 들어왔을때 로그아웃 작업을 수행
//서버에서 인증에 사용하는 모든 쿠키 값을 삭제하는 작업을 수행
//이미 리프레시 토큰에 문제가 있다면 로그아웃이 진행된 것으로 간주하고 400 에러를 반환
//로그아웃 요청시 200을 받든 400을 받든 사이트 접속시 첫 페이지로 보내주도록 작성
@AllArgsConstructor
public class Logout_Filter extends GenericFilterBean {

    private final JWTUtil jwtUtil;

    private final TokenrefreshRepository tokenrefreshRepository;

    private final CookieService cookieService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        doFilter((HttpServletRequest) request, (HttpServletResponse)  response, chain);
    }

    private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        //로그아웃 요청 경로 확인(localhost:8080/logout 형식으로 받아야함)
        String requestUri = request.getRequestURI();
        if (!requestUri.matches("^\\/logout$")) {

            filterChain.doFilter(request, response);
            return;
        }

        //로그아웃 요청 메소드 확인(post 메소드로 받아야함)
        String requestMethod = request.getMethod();
        if (!requestMethod.equals("POST")) {
            filterChain.doFilter(request, response);
            return;
        }

        //엑세스 토큰 얻음
        String access = null;
        Cookie[] cookies = request.getCookies();
        // 쿠키가 없는 경우
        if (cookies == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("access")) {
                    access = cookie.getValue();
                }
            }
        }
        else{
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        //엑세스 토큰 널값 확인
        if (access == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        //엑세스 토큰 만료 확인
        try {
            jwtUtil.isExpired(access);
        } catch (ExpiredJwtException e) {
            //만료시 예외 처리
            tokenrefreshRepository.deleteByUsername(jwtUtil.getUsername(jwtUtil.getUsername(access)));

            Cookie deleteAccessTokenCookie = cookieService.deleteCookie("access");

            response.addCookie(deleteAccessTokenCookie);

            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        // 토큰이 access인지 확인 (발급시 페이로드에 명시)
        String category = jwtUtil.getCategory(access);
        if (!category.equals("access")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        //로그아웃 진행
        //리프레시 토큰 DB에서 삭제
        tokenrefreshRepository.deleteByUsername(jwtUtil.getUsername(jwtUtil.getUsername(access)));

        //리프레시 토큰 쿠키값 제거
        Cookie deleteRefreshTokenCookie = cookieService.deleteCookie("refresh");

        response.addCookie(deleteRefreshTokenCookie);
        
        //엑세스 토큰 쿠키값 제거
        Cookie deleteAccessTokenCookie = cookieService.deleteCookie("access");

        response.addCookie(deleteAccessTokenCookie);

        response.setStatus(HttpServletResponse.SC_OK);


    }
}