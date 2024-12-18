package otpishAI.otpishAI_Backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="cart_num")
    private Long cartNum;

    private String username;

    @Column(name="product_detail_code")
    private String detailCode;

    @Column(name="detail_o_price")
    private Long oPrice;

    @Column(name="detail_r_price")
    private Long rPrice;

    @Column(name="total_price")
    private Long totalPrice;

    @Column(name="cart_quantity")
    private Long quantity;

    @Column(name="product_code")
    private String productCode;

    @Column(name="product_num")
    private Long productNum;


}