package otpishAI.otpishAI_Backend.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.filter.OncePerRequestFilter;
import otpishAI.otpishAI_Backend.entity.Seller;
import otpishAI.otpishAI_Backend.repository.SellerRepository;
import otpishAI.otpishAI_Backend.service.CookieService;
import otpishAI.otpishAI_Backend.repository.TokenrefreshRepository;
import otpishAI.otpishAI_Backend.entity.Tokenrefresh;

import java.io.IOException;
import java.util.Collections;

@AllArgsConstructor
public class SellerJWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final SellerRepository sellerRepository;
    private final CookieService cookieService;
    private final TokenrefreshRepository tokenrefreshRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String servletReqUrl = request.getServletPath();

        // /seller 경로로 시작하지 않는 요청은 필터를 통과시킴
        if (!servletReqUrl.startsWith("/seller")) {
            filterChain.doFilter(request, response);
            return;
        }

        // /seller/login, /seller/register 경로는 필터를 통과시킴
        if (servletReqUrl.contains("/seller/login") || servletReqUrl.contains("/seller/register")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Access 토큰 확인
        String accessToken = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("access")) {
                    accessToken = cookie.getValue();
                }
            }
        }

        // 토큰이 없는 경우
        if (accessToken == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // JWT 토큰 검증
        if (jwtUtil.isExpired(accessToken)) {
            // Access 토큰이 만료된 경우 refresh 토큰 확인 (DB에서 확인)
            String username = jwtUtil.getUsername(accessToken);
            String refreshToken = tokenrefreshRepository.findByUsername(username).map(Tokenrefresh::getRefresh).orElse(null);

            if (refreshToken == null || jwtUtil.isExpired(refreshToken)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            String role = jwtUtil.getRole(refreshToken);

            // 판매자 인증 확인
            Seller seller = sellerRepository.findBySellerId(username);
            if (seller == null || !role.equals("ROLE_SELLER")) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            // 새로운 Access 및 Refresh 토큰 발급
            String newAccessToken = jwtUtil.createJwt("access", username, role, 600000L);
            String newRefreshToken = jwtUtil.createJwt("refresh", username, role, 86400000L);

            // 리프레시 토큰 저장 DB에 기존의 리프레시 토큰 삭제 후 새 리프레시 토큰 저장
            tokenrefreshRepository.deleteByUsername(username);
            cookieService.addRefreshEntity(username, newRefreshToken, 86400000L);

            // 새로운 Access 토큰을 쿠키에 추가
            response.addCookie(cookieService.createCookie("access", newAccessToken));
            accessToken = newAccessToken;
        }

        String username = jwtUtil.getUsername(accessToken);
        String role = jwtUtil.getRole(accessToken);

        // 판매자 인증 확인
        Seller seller = sellerRepository.findBySellerId(username);
        if (seller == null || !role.equals("ROLE_SELLER")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // 인증 객체 생성 및 컨텍스트에 등록 (OAuth2 인증 필터를 건너뛸 수 있도록 설정)
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, null, Collections.singletonList(new SimpleGrantedAuthority(seller.getRole())));
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}
