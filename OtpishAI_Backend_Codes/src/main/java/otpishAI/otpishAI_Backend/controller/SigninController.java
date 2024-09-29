package otpishAI.otpishAI_Backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SigninController {

    @GetMapping("/signin/{signinType}")
    public ResponseEntity<?> signinNaver(@PathVariable("signinType") String signinType){
        System.out.println("Good");
        if(signinType.equals("naver")) {
            return new ResponseEntity<>("https://back.otpishai.shop/oauth2/authorization/naver", HttpStatus.OK);
        }
        else if(signinType.equals("google")) {
            return new ResponseEntity<>("https://back.otpishai.shop/oauth2/authorization/google", HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
