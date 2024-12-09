// 5. OrdersController - 주문 내역 저장 및 결제 검증 컨트롤러 (결제 검증 관련 오류 처리 추가)
package otpishAI.otpishAI_Backend.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import otpishAI.otpishAI_Backend.dto.CartResponseDTO;
import otpishAI.otpishAI_Backend.dto.CustomersDTO;
import otpishAI.otpishAI_Backend.dto.OrdersDTO;
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

    // 주문 상태 업데이트
    @PostMapping("/order/update/{orderNum}/{orderState}")
    public ResponseEntity<?> updateOrderState(@PathVariable("orderNum") Long orderNum, @PathVariable("orderState") Long orderState, HttpServletRequest request, HttpServletResponse response) {
        String refresh = refreshTCheckService.RefreshTCheck(request, response);

        if (!refresh.equals("")) {
            // 유저 정보 받아옴
            CustomersDTO customer = customersService.responseUser(jwtUtil.getUsername(refresh));
            Boolean isExist = ordersService.orderUpdate(orderNum, orderState);
            if (isExist) {
                List<OrdersDTO> ordersDTOList = ordersService.ordersDTOList(customer.getUsername());
                // 유저의 모든 주문 내역 반환
                return new ResponseEntity<>(ordersDTOList, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    // 주문 추가 및 결제 검증
    @PostMapping("/order/add")
    public ResponseEntity<?> addOrder(HttpServletRequest request, HttpServletResponse response, @RequestBody CartResponseDTO cartDTOList, @RequestParam("impUid") String impUid) {
        String refresh = refreshTCheckService.RefreshTCheck(request, response);

        if (!refresh.equals("")) {
            // 유저 정보 받아옴
            CustomersDTO customer = customersService.responseUser(jwtUtil.getUsername(refresh));

            // PortOne 결제 검증 및 주문 저장
            Boolean isSaved = ordersService.ordersSave(customer.getUsername(), cartDTOList.getCartDTOS(), impUid);
            if (isSaved) {
                // 사용자의 모든 주문 내역 반환
                List<OrdersDTO> ordersDTOList = ordersService.ordersDTOList(customer.getUsername());
                return new ResponseEntity<>(ordersDTOList, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
