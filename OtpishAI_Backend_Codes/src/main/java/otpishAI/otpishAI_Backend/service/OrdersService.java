package otpishAI.otpishAI_Backend.service;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.Payment;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import otpishAI.otpishAI_Backend.dto.CartDTO;
import otpishAI.otpishAI_Backend.dto.OrdersDTO;
import otpishAI.otpishAI_Backend.entity.Cart;
import otpishAI.otpishAI_Backend.entity.Orders;
import otpishAI.otpishAI_Backend.entity.Product;
import otpishAI.otpishAI_Backend.entity.ProductDetail;
import otpishAI.otpishAI_Backend.repository.CartRepository;
import otpishAI.otpishAI_Backend.repository.OrdersRepository;
import otpishAI.otpishAI_Backend.repository.ProductDetailRepository;
import otpishAI.otpishAI_Backend.repository.ProductRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrdersService {

    private final OrdersRepository ordersRepository;
    private final ProductDetailRepository productDetailRepository;
    private final CartRepository cartRepository;
    private final IamportClient iamportClient;
    private final ProductRepository productRepository;

    // 주문 내역 저장 및 결제 검증 (트랜잭션 적용)
    @Transactional
    @Modifying(clearAutomatically = true)
    public Boolean ordersSave(String ordersOwner, List<CartDTO> cartDTOS, String impUid) {
        try {
            // PortOne 결제 검증
            Payment payment = iamportClient.paymentByImpUid(impUid).getResponse();
            if (payment == null || !"paid".equals(payment.getStatus())) {
                return false; // 결제가 성공하지 않았으면 주문 내역 저장하지 않음
            }

            if (cartDTOS == null || cartDTOS.isEmpty()) {
                return false; // 장바구니가 비어있는 경우 처리하지 않음
            }

            Orders orders = new Orders();

            String[] productDetailCodes = cartDTOS.stream().map(CartDTO::getDetailCode).toArray(String[]::new);
            Long[] orderQuantities = cartDTOS.stream().map(CartDTO::getQuantity).toArray(Long[]::new);
            Long[] orderPrices = cartDTOS.stream().map(CartDTO::getRPrice).toArray(Long[]::new);

            // Null 체크 및 기본값 설정
            if (productDetailCodes.length == 0 || orderQuantities.length == 0 || orderPrices.length == 0) {
                return false; // 주문 데이터가 불완전한 경우 처리하지 않음
            }

            List<ProductDetail> productDetails = productDetailRepository.findAllByDetailCodeIn(productDetailCodes);
            if (productDetails.isEmpty()) {
                return false; // 상품 정보가 없는 경우 처리하지 않음
            }
            Long[] productNums = productDetails.stream().map(ProductDetail::getProductNum).toArray(Long[]::new);

            orders.setOrderOwner(ordersOwner);
            orders.setProductDetailCode(productDetailCodes);
            orders.setProductNum(productNums);
            orders.setOrderPrice(orderPrices);
            orders.setOrderQuantity(orderQuantities);
            orders.setOrderDate(LocalDateTime.now());
            orders.setTotalPrice(Arrays.stream(orderPrices).filter(Objects::nonNull).mapToLong(Long::longValue).sum());
            orders.setTotalQuantity(Arrays.stream(orderQuantities).filter(Objects::nonNull).mapToLong(Long::longValue).sum());
            orders.setOrderState(1L); // 상품 준비중 상태
            orders.setImpUid(impUid); // 결제의 고유 ID 저장
            orders.setPaymentStatus("paid"); // 결제 성공 상태 설정
            orders.setPaymentDate(LocalDateTime.now());

            ordersRepository.save(orders);
            Arrays.stream(productNums).forEach(productNum -> {
                Product product = productRepository.findByProductNum(productNum);
                if(product != null){
                    product.setPayment(product.getPayment() + 1L);
                    productRepository.save(product);
                }});
            // 장바구니에서 해당 항목 삭제
            List<Long> cartIds = cartDTOS.stream().map(CartDTO::getCartNum).collect(Collectors.toList());
            cartRepository.deleteAllById(cartIds);

            return true;
        } catch (IamportResponseException | IOException e) {
            e.printStackTrace();
            return false;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 주문 상태 업데이트
    public Boolean orderUpdate(Long orderNum, Long orderState) {
        try {
            Orders orders = ordersRepository.findByOrderNum(orderNum);
            if (orders == null) {
                return false;
            }
            orders.setOrderState(orderState);
            if (orderState == 3) { // 배송 완료 상태
                orders.setArrivalDate(LocalDateTime.now());
            }
            ordersRepository.save(orders);
            return true;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 특정 사용자의 모든 주문 내역 조회
    public List<OrdersDTO> ordersDTOList(String ordersOwner) {
        List<Orders> orders = ordersRepository.findAllByOrderOwner(ordersOwner);
        return orders.stream()
                .map(order -> new OrdersDTO(order))
                .collect(Collectors.toList());
    }
}