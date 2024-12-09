package otpishAI.otpishAI_Backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="seller")
public class Seller {

    @Id
    @Column(name="seller_id")
    private String sellerId;

    @Column(name="seller_email")
    private String sellerEmail;

    @Column(name="seller_phone")
    private String sellerPhone;

    @Column(name="seller_addr")
    private String sellerAddr;

    @Column(name="seller_pwd")
    private String sellerPwd;

    @Column(name="seller_name")
    private String sellerName;

    @Column(name="seller_brand_name")
    private String sellerBrandName;

    @Column(name="seller_img")
    private String sellerImg;

    @Column(name="seller_r_date")
    private LocalDateTime sellerRDate;

    @Column(name="role")
    private String role = "ROLE_SELLER"; // 기본 역할 설정

    // 권한 반환 메서드 추가
    public List<GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }
}