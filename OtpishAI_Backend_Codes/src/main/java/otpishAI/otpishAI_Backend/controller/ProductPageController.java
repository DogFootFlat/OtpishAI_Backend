package otpishAI.otpishAI_Backend.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import otpishAI.otpishAI_Backend.entity.Product;
import otpishAI.otpishAI_Backend.repository.ProductRepository;
import otpishAI.otpishAI_Backend.service.RefreshTCheckService;

import java.util.ArrayList;

@RestController
@AllArgsConstructor
public class ProductPageController {

    private final ProductRepository productRepository;

    private final RefreshTCheckService refreshTCheckService;

    @GetMapping("/product")
    public ResponseEntity<?> product(HttpServletRequest request, HttpServletResponse response){
        if(refreshTCheckService.RefreshTCheck(request, response).getStatusCode() == HttpStatus.OK)
        {
            String refresh = refreshTCheckService.getRefreshT(request, response);
            //유저 정보 받아옴
            ArrayList<Product> products = productRepository.findAllOrderByProduct_R_date();

            //유저 정보 반환
            return new ResponseEntity<>(products, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
