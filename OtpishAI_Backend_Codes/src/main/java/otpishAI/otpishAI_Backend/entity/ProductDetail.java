package otpishAI.otpishAI_Backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="product_detail")
public class ProductDetail {

    @Id
    @Column(name="product_detail_code")
    private String detailCode;

    @Column(name="product_num")
    private Integer productNum;

    @Column(name="product_color")
    private String productColor;

    @Column(name="product_size")
    private String productSize;

    @Column(name="product_inven")
    private Integer productInven;

    @Column(name="O_price")
    private Integer oPrice;

    @Column(name="R_price")
    private Integer rPrice;

}
