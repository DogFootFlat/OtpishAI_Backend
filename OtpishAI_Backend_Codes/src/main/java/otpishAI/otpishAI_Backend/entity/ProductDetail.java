package otpishAI.otpishAI_Backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Array;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="product_detail")
public class ProductDetail {

    @Id
    @Column(name="product_detail_code")
    private String detailCode;

    @Column(name="product_num")
    private Long productNum;

    @Column( name = "product_color")
    @JdbcTypeCode(SqlTypes.VECTOR)
    @Array(length = 3)
    private float[] productColor;

    @Column(name="product_size")
    private String productSize;

    @Column(name="product_inven")
    private Long productInven;

    @Column(name="detail_o_price")
    private Long oPrice;

    @Column(name="detail_r_price")
    private Long rPrice;

    @Column(name="product_code")
    private String productCode;

}
