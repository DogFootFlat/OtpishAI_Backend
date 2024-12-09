package otpishAI.otpishAI_Backend.config;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import otpishAI.otpishAI_Backend.jwt.*;
import otpishAI.otpishAI_Backend.oAuth2.FailureFilter;
import otpishAI.otpishAI_Backend.oAuth2.SuccessHandler;
import otpishAI.otpishAI_Backend.repository.SellerRepository;
import otpishAI.otpishAI_Backend.repository.TokenrefreshRepository;
import otpishAI.otpishAI_Backend.service.CookieService;
import otpishAI.otpishAI_Backend.service.OAuth2_UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import otpishAI.otpishAI_Backend.service.RefreshTCheckService;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    private final OAuth2_UserService customOAuth2UserService;

    private final SuccessHandler customSuccessHandler;

    private final FailureFilter failureFilter;

    private final TokenrefreshRepository tokenrefreshRepository;

    private final JWTUtil jwtUtil;

    private final CookieService cookieService;

    private final RefreshTCheckService refreshTCheckService;

    private final SellerRepository sellerRepository;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        //cors 보안 설정
        http
                .cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {

                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {

                        CorsConfiguration configuration = new CorsConfiguration();

                        configuration.setAllowedOriginPatterns(Arrays.asList("https://www.otpishai.shop", "https://back.otpishai.shop", "https://otpishai.shop"));
                        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PUT"));
                        configuration.setAllowCredentials(true);
                        configuration.setAllowedHeaders(Arrays.asList("JSESSIONID", "access", "refresh", "Set-Cookie", "Content-Type"));
                        configuration.setMaxAge(3600L);

                        configuration.setExposedHeaders(Arrays.asList("JSESSIONID", "access", "refresh", "Set-Cookie", "Content-Type"));

                        return configuration;
                    }
                }));
        //csrf 설정(비확성화)
        http
                .csrf((auth) -> auth.disable());

        //Form 로그인 방식 설정(비확성화)
        http
                .formLogin((auth) -> auth.disable());

        //HTTP Basic 인증 방식 설정(비확성화)
        http
                .httpBasic((auth) -> auth.disable());


        // OAuth2 설정 (사용자용)
        http.oauth2Login(oauth2 -> oauth2
                .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig
                        .userService(customOAuth2UserService))
                .successHandler(customSuccessHandler)
                .failureHandler(failureFilter)
        );

        // 권한 및 경로 설정
        http.authorizeRequests(auth -> auth
                .requestMatchers("/refresh/**", "/product/**", "/signin/**", "/product_detail/**", "/healthCheck", "/search", "/seller/login", "/seller/register", "api/judge").permitAll()
                .requestMatchers("/seller/**").hasRole("SELLER")
                .anyRequest().authenticated()
        );

        // 필터 설정 (필터 체인 순서 변경)
        http.addFilterAfter(new JWTFilter(jwtUtil, refreshTCheckService, tokenrefreshRepository, cookieService), OAuth2LoginAuthenticationFilter.class)
                .addFilterBefore(new SellerJWTFilter(jwtUtil, sellerRepository, cookieService, tokenrefreshRepository), JWTFilter.class)
                .addFilterBefore(new Logout_Filter(jwtUtil, tokenrefreshRepository, cookieService), SellerJWTFilter.class)
                .addFilterBefore(new SellerLogoutFilter(jwtUtil, tokenrefreshRepository, cookieService), Logout_Filter.class);

        // 세션 설정 (세션 사용 안함)
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}
