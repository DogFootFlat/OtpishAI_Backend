package otpishAI.otpishAI_Backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.List;

//유저 정보 엔티티
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="customers")
public class Customers {

    @Id
    private String email;

    private String username;
    private String name;

    private String role;

    private String nickname;
    private LocalDateTime birth;
    private String phone;
    private String profile_img;
    private Integer gender;

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name = "prefer_genre")
    private String[] preferGenre = new String[0];

    private Boolean is_suspended;
    private Boolean is_secessioned;
    private Long age;

    @Column(name="register_date")
    private LocalDateTime registerDate;

}
