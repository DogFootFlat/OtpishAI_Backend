package otpishAI.otpishAI_Backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import otpishAI.otpishAI_Backend.entity.Customers;
import otpishAI.otpishAI_Backend.entity.CustomersAddr;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomersDTO {

    //사용자의 정보들(Customers 테이블에서 참조한 값들)
    private String email;

    private String username;
    private String name;

    private String role;

    private String nickname;
    private LocalDateTime birth;
    private String phone;
    private String profile_img;
    private Integer gender;

    private String[] preferGenre = new String[0];

    private Boolean is_suspended;
    private Boolean is_secessioned;
    private Long age;

    private LocalDateTime registerDate;

    //사용자의 주소 목록(Customers_addr 테이블에서 참조한 값)
    private String[] addrs;
    //사용자의 대표 주소(Customers_addr 테이블에서 참조한 값)
    private String mainAddr;

    public CustomersDTO (Customers customers, CustomersAddr customersAddr){
        super();
        email = customers.getEmail();
        name = customers.getName();
        role = customers.getRole();
        age = customers.getAge();
        username = customers.getUsername();
        gender = customers.getGender();
        birth = customers.getBirth();
        phone = customers.getPhone();
        nickname = customers.getNickname();
        profile_img = customers.getProfile_img();
        preferGenre = customers.getPreferGenre();
        registerDate = customers.getRegisterDate();
        addrs = customersAddr.getAddrs();
        mainAddr = customersAddr.getMainAddr();
    }

}
