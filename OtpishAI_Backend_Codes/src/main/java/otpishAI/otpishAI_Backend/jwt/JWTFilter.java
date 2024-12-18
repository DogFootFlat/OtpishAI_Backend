package otpishAI.otpishAI_Backend.jwt;

import jakarta.servlet.http.Cookie;
import lombok.AllArgsConstructor;
import otpishAI.otpishAI_Backend.dto.OAuth2_User;
import otpishAI.otpishAI_Backend.dto.OAuth2_CustomersDTO;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import otpishAI.otpishAI_Backend.repository.TokenrefreshRepository;
import otpishAI.otpishAI_Backend.service.CookieService;
import otpishAI.otpishAI_Backend.service.RefreshTCheckService;

import java.io.IOException;
import java.io.PrintWriter;

//인증이 필요한 페이지로 이동할 때 마다 access 토큰을 확인하는 코드
//access 토큰은 만료 기간이 짧은 인증용 토큰으로, access 토큰이 유효 하지 않다면
//refresh 요청을 통해 새로 발급 받을 수 있음
//이때 access 토큰이 유효하지 않다면 401 에러를 반환
@AllArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final RefreshTCheckService refreshTCheckService;
    private final TokenrefreshRepository tokenrefreshRepository;
    private final CookieService cookieService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        String servletReqUrl = request.getServletPath();

        // /seller 경로로 시작하는 요청은 필터를 통과시킴
        if (servletReqUrl.startsWith("/seller")) {
            filterChain.doFilter(request, response);
            return;
        }

        if(servletReqUrl.contains("product") || servletReqUrl.contains("refresh") || servletReqUrl.contains("signin") || servletReqUrl.contains("product_detail") || servletReqUrl.contains("healthCheck") || servletReqUrl.contains("search") || servletReqUrl.contains("api"))
        {
            response.setStatus(HttpServletResponse.SC_OK);
            filterChain.doFilter(request, response);
            return;
        }
        System.out.println(request.getCookies());
        System.out.println(request.getHeaderNames());
        System.out.println(request.getRequestURI());
        System.out.println(request);
        //access 토큰 확인
        String accessToken = null;
        Cookie[] cookies = request.getCookies();
        // 쿠키가 없는 경우
        if (cookies == null || cookies.length == 0) {
            System.out.println("No cookie");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        for (Cookie cookie : cookies) {

            if (cookie.getName().equals("access")) {

                accessToken = cookie.getValue();
            }
        }
        // 토큰이 없다면 다음 필터로 넘김
        if (accessToken == null) {
            System.out.println("No token");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }


        // 토큰이 access인지 확인 (발급시 페이로드에 명시)
        String category = jwtUtil.getCategory(accessToken);
        if (!category.equals("access")) {
            PrintWriter writer = response.getWriter();
            writer.print("invalid access token");

            System.out.println("not access");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }


        // 토큰 만료 여부 확인, 만료시 다음 필터로 넘기지 않음
        if (jwtUtil.isExpired(accessToken)) {
            PrintWriter writer = response.getWriter();
            writer.print("access token expired");

            String refresh = refreshTCheckService.RefreshTCheck(request, response);

            if (refresh.equals("")) {
                System.out.println("No refresh");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            } else {
                String username = jwtUtil.getUsername(refresh);
                String role = jwtUtil.getRole(refresh);

                // 새로운 JWT 토큰 발급
                String newAccess = jwtUtil.createJwt("access", username, role, 600000L);
                String newRefresh = jwtUtil.createJwt("refresh", username, role, 86400000L);

                // 리프레시 토큰 저장 DB에 기존의 리프레시 토큰 삭제 후 새 리프레시 토큰 저장
                tokenrefreshRepository.deleteByUsername(username);
                cookieService.addRefreshEntity(username, newRefresh, 86400000L);

                response.addCookie(cookieService.createCookie("access", newAccess));
                System.out.println("Refreshed");
                accessToken = newAccess;
            }
        }


        //토큰에서 username과 role 획득
        String username = jwtUtil.getUsername(accessToken);
        String role = jwtUtil.getRole(accessToken);

        //userDTO를 생성하여 값 set
        OAuth2_CustomersDTO OAuth2CustomersDTO = new OAuth2_CustomersDTO();
        OAuth2CustomersDTO.setUsername(username);
        OAuth2CustomersDTO.setRole(role);

        //UserDetails에 회원 정보 객체 담기
        OAuth2_User oAuth_2User = new OAuth2_User(OAuth2CustomersDTO);

        //스프링 시큐리티 인증 토큰 생성
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(oAuth_2User.getUsername(), null, oAuth_2User.getAuthorities());
        //세션에 사용자 등록
        SecurityContextHolder.getContext().setAuthentication(authToken);

        response.setStatus(HttpServletResponse.SC_OK);
        filterChain.doFilter(request, response);
    }

}