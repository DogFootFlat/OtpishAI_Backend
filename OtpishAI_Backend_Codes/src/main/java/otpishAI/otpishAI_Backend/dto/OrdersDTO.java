package otpishAI.otpishAI_Backend.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import otpishAI.otpishAI_Backend.entity.Orders;

import java.time.LocalDateTime;
import java.util.Arrays;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrdersDTO {

    //주문 번호
    private Long orderNum;

    //주문 내역 주인
    private String orderOwner;

    //상품 번호들
    private Long[] productNum;

    //상품 상세 코드들(상품 번호와 1:1 매치)
    private String[] productDetailCode;

    //주문 날짜
    private LocalDateTime orderDate;

    //각 상품의 주문 수량(상품 번호와 1:1 매치)
    private Long[] orderQuantity;

    //총 주문 수량
    private Long totalQuantity;

    //각 상품의 가격(상품 번호와 1:1 매치, 각 상품의 주문 수량 * 각 상품의 rPrice)
    private Long[] orderPrice;

    //총 주문 가격
    private Long totalPrice;

    //배송 상태
    //상품 준비중 = 1
    //상품 배송중 = 2
    //상품 배송 완료 = 3
    //주문 취소됨 = 4
    //환불 완료 = 5
    //교환 완료됨 = 6
    private Long orderState;

    //배송 도착 날짜
    private LocalDateTime arrivalDate;

    public OrdersDTO(Orders orders){
        orderNum = orders.getOrderNum();
        orderOwner = orders.getOrderOwner();
        productNum = Arrays.copyOf(orders.getProductNum(), orders.getProductNum().length);
        productDetailCode = Arrays.copyOf(orders.getProductDetailCode(), orders.getProductDetailCode().length);
        orderDate = orders.getOrderDate();
        orderQuantity = Arrays.copyOf(orders.getOrderQuantity(), orders.getOrderQuantity().length);
        totalQuantity = orders.getTotalQuantity();
        orderPrice = Arrays.copyOf(orders.getOrderPrice(), orders.getOrderPrice().length);
        totalPrice = orders.getTotalPrice();

    }


}