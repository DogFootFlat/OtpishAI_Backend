
package otpishAI.otpishAI_Backend.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import otpishAI.otpishAI_Backend.dto.CustomersDTO;
import otpishAI.otpishAI_Backend.entity.Cart;
import otpishAI.otpishAI_Backend.entity.ProductDetail;
import otpishAI.otpishAI_Backend.jwt.JWTUtil;
import otpishAI.otpishAI_Backend.service.CartService;
import otpishAI.otpishAI_Backend.service.CustomersService;
import otpishAI.otpishAI_Backend.service.ProductService;
import otpishAI.otpishAI_Backend.service.RefreshTCheckService;

import java.util.List;

@RestController
@AllArgsConstructor
public class CartController {


    private final JWTUtil jwtUtil;

    private final RefreshTCheckService refreshTCheckService;

    private final CartService cartService;

    private final CustomersService customersService;

    private final ProductService productService;

    @PostMapping("/addCart/{productDetailCode}")
    public ResponseEntity<?> addCart(HttpServletRequest request, HttpServletResponse response, @PathVariable("productDetailCode") String productDetailCode){
        String refresh =refreshTCheckService.RefreshTCheck(request, response);

        if(!refresh.equals(""))
        {
            //장바구니 목록 업데이트
            CustomersDTO customer = customersService.responseUser(jwtUtil.getUsername(refresh));
            ProductDetail productDetail = productService.productDetailByDetailCode(productDetailCode);

            cartService.updateCart(customer.getUsername(), productDetail);


            return new ResponseEntity<>(HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

    }

    @GetMapping("/cartList")
    public ResponseEntity<?> register(HttpServletRequest request, HttpServletResponse response) {
        String refresh =refreshTCheckService.RefreshTCheck(request, response);

        if(!refresh.equals(""))
        {
            CustomersDTO customer = customersService.responseUser(jwtUtil.getUsername(refresh));
            List<Cart> cartList = cartService.cartssByUsername(customer.getUsername());

            return new ResponseEntity<>(cartList, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }


    @DeleteMapping("/deleteCart/{cartNum}")
    public ResponseEntity<?> deleteReview(@PathVariable("cartNum") Long cartNum, HttpServletRequest request, HttpServletResponse response)
    {
        String refresh =refreshTCheckService.RefreshTCheck(request, response);

        if(!refresh.equals(""))
        {
            cartService.deleteCart(cartNum);

            CustomersDTO customer = customersService.responseUser(jwtUtil.getUsername(refresh));
            List<Cart> cartList = cartService.cartssByUsername(customer.getUsername());

            return new ResponseEntity<>(cartList, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
