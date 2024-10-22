
package otpishAI.otpishAI_Backend.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import otpishAI.otpishAI_Backend.dto.CartDTO;
import otpishAI.otpishAI_Backend.dto.CartResponseDTO;
import otpishAI.otpishAI_Backend.dto.CustomersDTO;
import otpishAI.otpishAI_Backend.dto.WishlistDTO;
import otpishAI.otpishAI_Backend.jwt.JWTUtil;
import otpishAI.otpishAI_Backend.service.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class OrdersController {


    private final JWTUtil jwtUtil;

    private final RefreshTCheckService refreshTCheckService;

    private final CustomersService customersService;

    private final OrdersService ordersService;

    private final WishlistService wishlistService;

    private final ReviewService reviewService;

    @PostMapping("/order/update/{orderNum}/{orderState}")
    public ResponseEntity<?> updateWishlist(@PathVariable("orderNum") Long orderNum, @PathVariable("orderState") Long orderState, HttpServletRequest request, HttpServletResponse response){
        String refresh =refreshTCheckService.RefreshTCheck(request, response);

        if(!refresh.equals(""))
        {
            //유저 정보 받아옴
            CustomersDTO customer = customersService.responseUser(jwtUtil.getUsername(refresh));

            Boolean isExist = ordersService.orderUpdate(orderNum, orderState);
            if(isExist)
            {
                WishlistDTO wishlistDTO = wishlistService.wishlistList(customer.getUsername());
                //유저 정보 반환
                return new ResponseEntity<>(wishlistDTO, HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("order/add/{cartList}")
    public ResponseEntity<?> addOrder(@PathVariable("cartList") CartResponseDTO cartDTOList, HttpServletRequest request, HttpServletResponse response){
        String refresh =refreshTCheckService.RefreshTCheck(request, response);

        if(!refresh.equals(""))
        {
            //유저 정보 받아옴
            CustomersDTO customer = customersService.responseUser(jwtUtil.getUsername(refresh));

            Boolean isExist = ordersService.ordersSave(customer.getUsername(), cartDTOList.getCartDTOS());
            if(isExist)
            {
                WishlistDTO wishlistDTO = wishlistService.wishlistList(customer.getUsername());
                //유저 정보 반환
                return new ResponseEntity<>(wishlistDTO, HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
