package otpishAI.otpishAI_Backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="orders")
public class Orders {

    
    //주문 번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="order_num ")
    private Long orderNum;

    //주문자(email)
    @Column(name="order_owner")
    private String orderOwner;

    //상품 번호
    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name="product_num")
    private Long[] productNum;

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name="product_detail_code")
    private String[] productDetailCode;

    //주문 날짜
    @Column(name="order_date")
    private LocalDateTime orderDate;

    //주문 수량
    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name="order_quantity")
    private Long[] orderQuantity;

    //총 주문 수량
    @Column(name="total_quantity")
    private Long totalQuantity;

    //상품 가격
    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name="order_price")
    private Long[] orderPrice;

    //총 가격
    @Column(name="total_price")
    private Long totalPrice;

    //배송 상태
    //상품 준비중 = 1
    //상품 배송중 = 2
    //상품 배송 완료 = 3
    //주문 취소됨 = 4
    //환불 완료 = 5
    //교환 완료됨 = 6
    @Column(name="order_state")
    private Long orderState;

    //배송 도착 날짜
    @Column(name="arrival_date")
    private LocalDateTime arrivalDate;


    // 결제 관련 필드 추가
    @Column(name="imp_uid", unique = true)
    private String impUid; // PortOne의 고유 결제 ID

    @Column(name="payment_status")
    private String paymentStatus; // 결제 상태 (예: "paid", "failed")

    @Column(name="payment_date")
    private LocalDateTime paymentDate; // 결제 완료 날짜


}