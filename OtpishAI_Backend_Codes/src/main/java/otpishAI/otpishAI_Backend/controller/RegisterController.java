package otpishAI.otpishAI_Backend.controller;

import lombok.AllArgsConstructor;
import otpishAI.otpishAI_Backend.dto.CustomersDTO;
import otpishAI.otpishAI_Backend.dto.OAuth2_CustomersDTO;
import otpishAI.otpishAI_Backend.entity.Customers;
import otpishAI.otpishAI_Backend.jwt.JWTUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import otpishAI.otpishAI_Backend.service.RefreshTCheckService;
import otpishAI.otpishAI_Backend.service.CustomersService;

@RestController
@AllArgsConstructor
public class RegisterController {


    private final JWTUtil jwtUtil;

    private final RefreshTCheckService refreshTCheckService;

    private final CustomersService customersService;

    @GetMapping("/register")
    public ResponseEntity<?> register(HttpServletRequest request, HttpServletResponse response){
        String refresh =refreshTCheckService.RefreshTCheck(request, response);

        if(!refresh.equals(""))
        {
            //유저 정보 받아옴
            CustomersDTO customer = customersService.responseUser(jwtUtil.getUsername(refresh));

            //유저 정보 반환
            return new ResponseEntity<>(customer, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/register")
    public void register(@RequestBody CustomersDTO customers) {
        //DB에 유저 정보 저장
        customersService.saveUser(customers);
    }
}
