package otpishAI.otpishAI_Backend.dto;

import lombok.Getter;
import lombok.Setter;
import otpishAI.otpishAI_Backend.entity.Customers;

import java.time.LocalDateTime;

@Getter
@Setter
public class CustomersDTO {

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

    private String[] preferGenre = new String[0];

    private Boolean is_suspended;
    private Boolean is_secessioned;
    private Long age;

    public CustomersDTO (Customers customers){
        super();
        email = customers.getEmail();
        addr = customers.getAddr();
        name = customers.getName();
        role = customers.getRole();
        age = customers.getAge();
        username = customers.getUsername();
        gender = customers.getGender();
        birth = customers.getBirth();
        phone = customers.getPhone();
        nickname = customers.getNickname();
        profile_img = customers.getProfile_img();
    }

}
