package otpishAI.otpishAI_Backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

//상품 정보 엔티티
@Entity
@Getter
@Setter
public class Product {

    //품번
    @Id
    @Column(name="product_code")
    private String productCode;

    //상품명
    @Column(name="product_name")
    private String productName;

    @Column(name="product_img_0")
    private String productImg0;
    @Column(name="product_img_1")
    private String productImg1;
    @Column(name="product_img_2")
    private String productImg2;
    @Column(name="product_img_3")
    private String productImg3;
    @Column(name="product_img_4")
    private String productImg4;
    @Column(name="product_img_5")
    private String productImg5;

    //정가
    @Column(name="O_price")
    private Integer oPrice;
    //판매가
    @Column(name="R_price")
    private Integer rPrice;

    @Column(name="category_1")
    private String category1;
    @Column(name="category_2")
    private String category2;
    @Column(name="category_3")
    private String category3;
    //옷 분류(앞자리 3개로 대분류, 뒷자리 3개로 소분류)
    @Column(name="genre_code")
    private String genreCode;

    //결재 건수
    @Column(name="payment_N")
    private Integer payment;
    //찜 횟수
    @Column(name="favorite_N")
    private Integer favorite;
    //리뷰 수
    @Column(name="review_N")
    private Integer review;
    //등록일
    @Column(name="product_R_date")
    private Date productRdate;

    //상품 삭제 여부
    @Column(name="is_deleted")
    private Integer isDeleted;
    private String judge;
    @Column(name="product_brand")
    private String productBrand;

    //상품의 대상 성별(1:유니섹스 2:여성 3:남성)
    @Column(name="product_gender")
    private Integer productGender;

    @Column(name="product_registrant")
    private String productRegistrant;

    @Column(name="product_num")
    private Integer productNum;

}
