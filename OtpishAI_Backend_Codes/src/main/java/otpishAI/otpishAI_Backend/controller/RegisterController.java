package otpishAI.otpishAI_Backend.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import otpishAI.otpishAI_Backend.dto.CustomersDTO;
import otpishAI.otpishAI_Backend.dto.OAuth2_CustomersDTO;
import otpishAI.otpishAI_Backend.entity.Customers;
import otpishAI.otpishAI_Backend.jwt.JWTUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import otpishAI.otpishAI_Backend.service.RefreshTCheckService;
import otpishAI.otpishAI_Backend.service.CustomersService;

import java.io.IOException;

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
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody CustomersDTO customers,
                                      @RequestParam(value = "customerimg", required = false) MultipartFile customerFile) {

        String imgPath = "";
        if (customerFile != null) {
            try {
                // 각 파일을 저장하고 반환된 경로를 리스트에 추가
                String imagePath = customersService.saveImage(customerFile);
                imgPath = imagePath;
            } catch (IOException e) {
                e.printStackTrace();
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }

        // ReviewDTO에 이미지 경로 리스트를 배열로 설정 (이미지가 없을 경우 빈 배열로 설정)
        customers.setProfile_img(imgPath);
        //DB에 유저 정보 저장
        if(customersService.saveUser(customers)){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @PutMapping("/register")
    public ResponseEntity<?> update(@RequestBody CustomersDTO customers,
                                      @RequestParam(value = "customerimg", required = false) MultipartFile customerFile) {

        String imgPath = "";
        if (customerFile != null) {
            try {
                // 각 파일을 저장하고 반환된 경로를 리스트에 추가
                String imagePath = customersService.saveImage(customerFile);
                imgPath = imagePath;
            } catch (IOException e) {
                e.printStackTrace();
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }

        // ReviewDTO에 이미지 경로 리스트를 배열로 설정 (이미지가 없을 경우 빈 배열로 설정)
        customers.setProfile_img(imgPath);
        //DB에 유저 정보 저장
        if(customersService.updateUser(customers)){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
