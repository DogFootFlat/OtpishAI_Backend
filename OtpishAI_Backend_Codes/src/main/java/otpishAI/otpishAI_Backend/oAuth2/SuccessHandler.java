package otpishAI.otpishAI_Backend.oAuth2;

import jakarta.servlet.http.Cookie;
import lombok.AllArgsConstructor;
import otpishAI.otpishAI_Backend.dto.OAuth2_User;
import otpishAI.otpishAI_Backend.jwt.JWTUtil;
import otpishAI.otpishAI_Backend.repository.TokenrefreshRepository;
import otpishAI.otpishAI_Backend.repository.CustomersRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import otpishAI.otpishAI_Backend.service.CookieService;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

@Component
@AllArgsConstructor
public class SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final CustomersRepository customersRepository;
    private final JWTUtil jwtUtil;
    private final CookieService cookieService;
    private final TokenrefreshRepository tokenrefreshRepository;

    //로그인 성공시 실행
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        //OAuth2User
        OAuth2_User customUserDetails = (OAuth2_User) authentication.getPrincipal();

        String username = customUserDetails.getUsername();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        //JWT access 토큰과 리프레시 토큰 생성
        String access = jwtUtil.createJwt("access",  username, role, 600000L); //access 토큰의 지속 시간은 10분
        String refresh = jwtUtil.createJwt("refresh", username, role, 86400000L); //리프레시 토큰의 지속 시간은 24시간

        tokenrefreshRepository.deleteByUsername(username);
        cookieService.addRefreshEntity(username, refresh, 86400000L);

        Cookie accessCookie = cookieService.createCookie("access", access);

        String cookieHeader = accessCookie.getName() + "=" + accessCookie.getValue() + "; Path=" + accessCookie.getPath() + "; HttpOnly; Secure; SameSite=None";
        response.setHeader("Set-Cookie", cookieHeader);

        System.out.println(access);
        System.out.println("logged in");
        if(customersRepository.findByUsername(jwtUtil.getUsername(access)).getRegisterDate() == null)
        {
            response.sendRedirect("https://otpishai.shop/sign-up");
        }
        else {
            response.sendRedirect("https://otpishai.shop/products");

        }

    }

}