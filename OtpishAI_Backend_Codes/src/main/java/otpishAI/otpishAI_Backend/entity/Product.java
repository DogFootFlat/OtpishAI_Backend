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
    private String productcode;

    //상품명
    @Column(name="product_name")
    private String productname;

    @Column(name="product_img_0")
    private String productimg0;
    @Column(name="product_img_1")
    private String productimg1;
    @Column(name="product_img_2")
    private String productimg2;
    @Column(name="product_img_3")
    private String productimg3;
    @Column(name="product_img_4")
    private String productimg4;
    @Column(name="product_img_5")
    private String productimg5;

    //정가
    @Column(name="O_price")
    private Integer oprice;
    //판매가
    @Column(name="R_price")
    private Integer rprice;

    @Column(name="category_1")
    private String category1;
    @Column(name="category_2")
    private String category2;
    @Column(name="category_3")
    private String category3;
    //옷 분류(앞자리 3개로 대분류, 뒷자리 3개로 소분류)
    @Column(name="genre_code")
    private String genrecode;

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
    private Date productrdate;
    //상품 삭제 여부
    @Column(name="is_deleted")
    private Integer isdeleted;
    private String judge;
    @Column(name="product_brand")
    private String productbrand;

}
