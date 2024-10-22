
package otpishAI.otpishAI_Backend.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import otpishAI.otpishAI_Backend.dto.CustomersDTO;
import otpishAI.otpishAI_Backend.dto.OrdersDTO;
import otpishAI.otpishAI_Backend.dto.ReviewDTO;
import otpishAI.otpishAI_Backend.dto.WishlistDTO;
import otpishAI.otpishAI_Backend.jwt.JWTUtil;
import otpishAI.otpishAI_Backend.service.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class WishlistController {


    private final JWTUtil jwtUtil;

    private final RefreshTCheckService refreshTCheckService;

    private final CustomersService customersService;

    private final WishlistService wishlistService;


    @PostMapping("/wishlist/update/{productNum}")
    public ResponseEntity<?> updateWishlist(@PathVariable("productNum") Long productNum, HttpServletRequest request, HttpServletResponse response){
        String refresh =refreshTCheckService.RefreshTCheck(request, response);

        if(!refresh.equals(""))
        {
            //유저 정보 받아옴
            CustomersDTO customer = customersService.responseUser(jwtUtil.getUsername(refresh));
            wishlistService.addOrDeleteWishlist(customer.getUsername(), productNum);
            WishlistDTO wishlistDTO = wishlistService.wishlistList(customer.getUsername());

            //유저 정보 반환
            return new ResponseEntity<>(wishlistDTO, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }


}
