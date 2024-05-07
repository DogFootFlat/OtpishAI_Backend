package otpishAI.otpishAI_Backend.entity;

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
    private String product_code;

    //상품명
    private String product_name;

    private String product_img_0;
    private String product_img_1;
    private String product_img_2;
    private String product_img_3;
    private String product_img_4;
    private String product_img_5;

    //정가
    private Integer O_price;
    //판매가
    private Integer R_price;

    private String category_1;
    private String category_2;
    private String category_3;
    //옷 분류(앞자리 3개로 대분류, 뒷자리 3개로 소분류)
    private String genre_code;

    //결재 건수
    private Integer payment_N;
    //찜 횟수
    private Integer favorite_N;
    //리뷰 수
    private Integer review_N;
    //등록일
    private Date product_R_date;
    //상품 삭제 여부
    private Integer is_deleted;
    private String judge;

    private Integer product_brand;

}
