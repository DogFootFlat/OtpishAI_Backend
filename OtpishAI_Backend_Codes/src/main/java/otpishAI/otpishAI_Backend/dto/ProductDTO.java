package otpishAI.otpishAI_Backend.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Array;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import otpishAI.otpishAI_Backend.entity.Product;
import otpishAI.otpishAI_Backend.entity.ProductDetail;
import otpishAI.otpishAI_Backend.entity.Review;

import java.time.LocalDateTime;
import java.util.List;

//상품 정보 엔티티
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    //품번
    private String productCode;

    //상품명
    private String productName;

    private String[] productImg  = new String[0];

    private String[] productInfo  = new String[0];

    //정가
    private Long oPrice;
    //판매가
    private Long rPrice;

    private String[] category = new String[0];

    //옷 분류(앞자리 3개로 대분류, 뒷자리 3개로 소분류)
    private String genreCode;

    //결재 건수
    private Long payment;
    //찜 횟수
    private Long favorite;
    //리뷰 수
    private Long review;
    //등록일
    private LocalDateTime productRdate;

    //상품 삭제 여부
    private Integer isDeleted;
    private Long judge;
    private String productBrand;

    private Integer productGender;

    private String productRegistrant;
    private Long productNum;

    private ProductDetail[] productDetail;

    public ProductDTO(Product product, List<ProductDetail> productDetails){

        //품번
        productCode = product.getProductCode();

        //상품명
        productName = product.getProductName();

        productImg  = product.getProductImg();

        productInfo  = product.getProductInfo();

        //정가
        oPrice = product.getOPrice();
        //판매가
        rPrice = product.getRPrice();

        category = product.getCategory();

        //옷 분류(앞자리 3개로 대분류, 뒷자리 3개로 소분류)
        genreCode = product.getGenreCode();

        //결재 건수
        payment = product.getPayment();
        //찜 횟수
        favorite = product.getFavorite();
        //리뷰 수
        review = product.getReview();
        //등록일
        productRdate = product.getProductRdate();

        //상품 삭제 여부
        isDeleted = product.getIsDeleted();
        judge = product.getJudge();
        productBrand = product.getProductBrand();

        productGender = product.getProductGender();

        productRegistrant = product.getProductRegistrant();
        productNum = product.getProductNum();

        productDetail = productDetails.toArray(new ProductDetail[0]);

    }

}
