package otpishAI.otpishAI_Backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

//상품 정보 엔티티
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="product")
public class Product {

    //품번
    @Column(name="product_code")
    private String productCode;

    //상품명
    @Column(name="product_name")
    private String productName;


    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name = "product_img")
    private String[] productImg  = new String[0];


    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name = "product_info")
    private String[] productInfo  = new String[0];

    //정가
    @Column(name="o_price")
    private Long oPrice;
    //판매가
    @Column(name="r_price")
    private Long rPrice;

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name = "category")
    private String[] category = new String[0];

    //옷 분류(앞자리 3개로 대분류, 뒷자리 3개로 소분류)
    @Column(name="genre_code")
    private String genreCode;

    //결재 건수
    @Column(name="payment_n")
    private Long payment;
    //찜 횟수
    @Column(name="favorite_n")
    private Long favorite;
    //리뷰 수
    @Column(name="review_n")
    private Long review;
    //등록일
    @Column(name="product_r_date")
    private LocalDateTime productRdate;

    //상품 삭제 여부
    @Column(name="is_deleted")
    private Integer isDeleted;
    private Long judge;
    @Column(name="product_brand")
    private String productBrand;

    //상품의 대상 성별(1:유니섹스 2:여성 3:남성)
    @Column(name="product_gender")
    private Integer productGender;

    @Column(name="product_registrant")
    private String productRegistrant;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="product_num")
    private Long productNum;

}
