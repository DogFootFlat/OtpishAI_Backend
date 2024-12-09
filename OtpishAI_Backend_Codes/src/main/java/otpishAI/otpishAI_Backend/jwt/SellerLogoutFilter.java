package otpishAI.otpishAI_Backend.jwt;

import lombok.AllArgsConstructor;
import otpishAI.otpishAI_Backend.repository.TokenrefreshRepository;
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

// 판매자 로그아웃 요청을 처리하는 필터
@AllArgsConstructor
public class SellerLogoutFilter extends GenericFilterBean {

    private final JWTUtil jwtUtil;
    private final TokenrefreshRepository tokenrefreshRepository;
    private final CookieService cookieService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        doFilter((HttpServletRequest) request, (HttpServletResponse) response, chain);
    }

    private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        // 로그아웃 요청 경로 확인 (/seller/logout 형식으로 받아야 함)
        String requestUri = request.getRequestURI();
        if (!requestUri.matches("\\/seller\\/logout$")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 로그아웃 요청 메소드 확인 (POST 메소드로 받아야 함)
        String requestMethod = request.getMethod();
        if (!requestMethod.equals("POST")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 액세스 토큰 얻음
        String access = null;
        Cookie[] cookies = request.getCookies();
        // 쿠키가 없는 경우
        if (cookies == null || cookies.length == 0) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("access")) {
                access = cookie.getValue();
            }
        }

        // 액세스 토큰 널값 확인
        if (access == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // 액세스 토큰 만료 확인
        if (jwtUtil.isExpired(access)) {
            // 만료 시 예외 처리
            tokenrefreshRepository.deleteByUsername(jwtUtil.getUsername(access));

            Cookie deleteAccessTokenCookie = cookieService.deleteCookie("access");
            response.addCookie(deleteAccessTokenCookie);

            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        // 토큰이 access인지 확인 (발급 시 페이로드에 명시)
        String category = jwtUtil.getCategory(access);
        if (!category.equals("access")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // 로그아웃 진행
        // 리프레시 토큰 DB에서 삭제
        tokenrefreshRepository.deleteByUsername(jwtUtil.getUsername(access));

        // 액세스 토큰 쿠키값 제거
        Cookie deleteAccessTokenCookie = cookieService.deleteCookie("access");
        response.addCookie(deleteAccessTokenCookie);

        response.setStatus(HttpServletResponse.SC_OK);
    }
}
