package otpishAI.otpishAI_Backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
public class ProductDetailDTO {

    //품번
    private String productCode;

    //상품명
    private String productName;

    private String[] productImg;

    private String[] productInfo;

    //정가
    private Long oPrice;
    //판매가
    private Long rPrice;

    private String[] category;
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
    private String judge;
    private String productBrand;

    //상품의 대상 성별(1:유니섹스 2:여성 3:남성)
    private Integer productGender;

    private String productRegistrant;

    private Long productNum;


    private List<ProductDetail> productDetails;

    private List<Review> reviews;

    public ProductDetailDTO(Product product, List<ProductDetail> productDetails, List<Review> reviews){
        super();
        productCode = product.getProductCode();

        productName = product.getProductName();

        productImg = product.getProductImg();
        productInfo = product.getProductInfo();

        oPrice = product.getOPrice();
        rPrice = product.getRPrice();

        category = product.getCategory();

        genreCode = product.getGenreCode();

        payment = product.getPayment();

        favorite = product.getFavorite();

        review = product.getReview();

        productRdate = product.getProductRdate();

        isDeleted = product.getIsDeleted();

        judge = product.getJudge();

        productBrand = product.getProductBrand();

        productGender = product.getProductGender();

        productRegistrant = product.getProductRegistrant();

        productNum = product.getProductNum();

        this.productDetails = productDetails;
        this.reviews = reviews;
    }
}
