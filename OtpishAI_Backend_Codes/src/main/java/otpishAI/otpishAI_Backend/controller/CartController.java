
package otpishAI.otpishAI_Backend.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import otpishAI.otpishAI_Backend.dto.CartDTO;
import otpishAI.otpishAI_Backend.dto.CartResponseDTO;
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

    @PostMapping("/addCart/{productCode}")
    public ResponseEntity<?> addCart(HttpServletRequest request, HttpServletResponse response, @PathVariable("productCode") String productCode){
        String refresh =refreshTCheckService.RefreshTCheck(request, response);

        if(!refresh.equals(""))
        {
            //장바구니 목록 업데이트
            CustomersDTO customer = customersService.responseUser(jwtUtil.getUsername(refresh));
            ProductDetail productDetail = productService.productDetailByCode(productCode);

            cartService.addOrUpdateCart(customer.getUsername(), productDetail, productCode);

            CartResponseDTO cartResponseDTO = cartService.cartsByUsername(customer.getUsername());

            return new ResponseEntity<>(cartResponseDTO, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

    }

    @PostMapping("/addCartDetail/{productCode}/{productDetailCode}")
    public ResponseEntity<?> addDetailCart(HttpServletRequest request, HttpServletResponse response, @PathVariable("productCode") String productCode, @PathVariable("productDetailCode") String productDetailCode){
        String refresh =refreshTCheckService.RefreshTCheck(request, response);

        if(!refresh.equals(""))
        {
            //장바구니 목록 업데이트
            CustomersDTO customer = customersService.responseUser(jwtUtil.getUsername(refresh));
            ProductDetail productDetail = productService.productDetailByDetailCode(productDetailCode);
            cartService.addOrUpdateCart(customer.getUsername(), productDetail, productCode);

            CartResponseDTO cartResponseDTO = cartService.cartsByUsername(customer.getUsername());

            return new ResponseEntity<>(cartResponseDTO, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

    }

    @GetMapping("/cartList")
    public ResponseEntity<?> cartList(HttpServletRequest request, HttpServletResponse response) {
        String refresh =refreshTCheckService.RefreshTCheck(request, response);

        if(!refresh.equals(""))
        {
            CustomersDTO customer = customersService.responseUser(jwtUtil.getUsername(refresh));
            CartResponseDTO cartResponseDTO = cartService.cartsByUsername(customer.getUsername());

            return new ResponseEntity<>(cartResponseDTO, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }


    @PostMapping("/minusCart/{cartNum}")
    public ResponseEntity<?> minusCart(@PathVariable("cartNum") Long cartNum, HttpServletRequest request, HttpServletResponse response)
    {
        String refresh =refreshTCheckService.RefreshTCheck(request, response);

        if(!refresh.equals(""))
        {
            Boolean cartExist = cartService.minusCart(cartNum);
            if(!cartExist){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            CustomersDTO customer = customersService.responseUser(jwtUtil.getUsername(refresh));
            CartResponseDTO cartResponseDTO = cartService.cartsByUsername(customer.getUsername());

            return new ResponseEntity<>(cartResponseDTO, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }


    @DeleteMapping("/deleteCart/{cartNum}")
    public ResponseEntity<?> deleteCart(@PathVariable("cartNum") Long cartNum, HttpServletRequest request, HttpServletResponse response)
    {
        String refresh =refreshTCheckService.RefreshTCheck(request, response);

        if(!refresh.equals(""))
        {
            Boolean cartExist = cartService.deleteCart(cartNum);
            if(!cartExist){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            CustomersDTO customer = customersService.responseUser(jwtUtil.getUsername(refresh));
            CartResponseDTO cartResponseDTO = cartService.cartsByUsername(customer.getUsername());

            return new ResponseEntity<>(cartResponseDTO, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }


    @PostMapping("/plusCart/{cartNum}")
    public ResponseEntity<?> plusCart(@PathVariable("cartNum") Long cartNum, HttpServletRequest request, HttpServletResponse response)
    {
        String refresh =refreshTCheckService.RefreshTCheck(request, response);

        if(!refresh.equals(""))
        {
            Boolean cartExist = cartService.plusCart(cartNum);
            if(!cartExist){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            CustomersDTO customer = customersService.responseUser(jwtUtil.getUsername(refresh));
            CartResponseDTO cartResponseDTO = cartService.cartsByUsername(customer.getUsername());

            return new ResponseEntity<>(cartResponseDTO, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/syncCart")
    public ResponseEntity<?> syncCart(HttpServletRequest request, HttpServletResponse response, @RequestBody List<String> productDetailCodes){

        String refresh =refreshTCheckService.RefreshTCheck(request, response);

        if(!refresh.equals(""))
        {

            CustomersDTO customer = customersService.responseUser(jwtUtil.getUsername(refresh));
            productDetailCodes.forEach(productDetailCode->{
                Cart cart = cartService.cartFind(productDetailCode);
                cartService.addOrUpdateCart(customer.getUsername(), productService.productDetailByDetailCode(productDetailCode), cart.getProductCode());
            });

            CartResponseDTO cartResponseDTO = cartService.cartsByUsername(customer.getUsername());

            return new ResponseEntity<>(cartResponseDTO, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

    }
}
