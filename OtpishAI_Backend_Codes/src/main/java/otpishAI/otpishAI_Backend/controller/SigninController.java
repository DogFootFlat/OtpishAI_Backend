package otpishAI.otpishAI_Backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SigninController {

    @GetMapping("/signin/{signinType}")
    public String signinNaver(@PathVariable("signinType") String signinType){
        if(signinType.equals("naver")) {
            return "http://localhost:8080/oauth2/authorization/naver";
        }
        else if(signinType.equals("google")) {
            return "http://localhost:8080/oauth2/authorization/google";
        }
        else {
            return "SignInType Unmatched";
        }
    }
}
