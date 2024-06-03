package otpishAI.otpishAI_Backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import java.time.LocalDateTime;
import java.util.List;

//유저 정보 엔티티
@Entity
@Getter
@Setter
@Table(name="customers")
public class Customers {

    @Id
    private String email;

    private String username;
    private String name;

    private String role;

    private String addr;
    private String nickname;
    private LocalDateTime birth;
    private String phone;
    private String profile_img;
    private Integer gender;

    @Type(value = StringArrayType.class)
    @Column(name = "prefer_genre", columnDefinition = "text[]")
    private String[] preferGenre = new String[0];

    private Boolean is_suspended;
    private Boolean is_secessioned;
    private Long age;


}
