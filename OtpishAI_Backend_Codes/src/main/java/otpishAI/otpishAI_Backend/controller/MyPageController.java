
package otpishAI.otpishAI_Backend.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import otpishAI.otpishAI_Backend.dto.*;
import otpishAI.otpishAI_Backend.entity.ProductDetail;
import otpishAI.otpishAI_Backend.jwt.JWTUtil;
import otpishAI.otpishAI_Backend.service.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class MyPageController {


    private final JWTUtil jwtUtil;

    private final RefreshTCheckService refreshTCheckService;

    private final CustomersService customersService;

    private final OrdersService ordersService;

    private final WishlistService wishlistService;

    private final ReviewService reviewService;

    @GetMapping("/profile")
    public ResponseEntity<?> userProfile(HttpServletRequest request, HttpServletResponse response){
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

    @GetMapping("/order/history")
    public ResponseEntity<?> orderHistory(HttpServletRequest request, HttpServletResponse response){
        String refresh =refreshTCheckService.RefreshTCheck(request, response);

        if(!refresh.equals(""))
        {
            //유저 정보 받아옴
            CustomersDTO customer = customersService.responseUser(jwtUtil.getUsername(refresh));

            List<OrdersDTO> ordersDTOs = ordersService.ordersDTOList(customer.getUsername());
            return new ResponseEntity<>(ordersDTOs, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

    }

    @GetMapping("/wishlist/list")
    public ResponseEntity<?> wishlistList(HttpServletRequest request, HttpServletResponse response){
        String refresh =refreshTCheckService.RefreshTCheck(request, response);

        if(!refresh.equals(""))
        {
            //유저 정보 받아옴
            CustomersDTO customer = customersService.responseUser(jwtUtil.getUsername(refresh));

            WishlistDTO wishlistDTO = wishlistService.wishlistList(customer.getUsername());

            return new ResponseEntity<>(wishlistDTO, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/review/list")
    public ResponseEntity<?> reviewList(HttpServletRequest request, HttpServletResponse response){
        String refresh =refreshTCheckService.RefreshTCheck(request, response);

        if(!refresh.equals(""))
        {
            //유저 정보 받아옴
            CustomersDTO customer = customersService.responseUser(jwtUtil.getUsername(refresh));

            List<ReviewDTO> reviewDTO = reviewService.reviewsByReviewOwner(customer.getUsername());

            return new ResponseEntity<>(reviewDTO, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
