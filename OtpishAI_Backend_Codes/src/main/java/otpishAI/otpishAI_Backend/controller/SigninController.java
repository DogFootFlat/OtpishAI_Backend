package otpishAI.otpishAI_Backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SigninController {

    @GetMapping("/signin/naver")
    public String signinNaver(){
        return "http://localhost:8080/oauth2/authorization/naver";
    }
    @GetMapping("/signin/google")
    public String signinGoogle(){
        return "http://localhost:8080/oauth2/authorization/google";
    }
}
