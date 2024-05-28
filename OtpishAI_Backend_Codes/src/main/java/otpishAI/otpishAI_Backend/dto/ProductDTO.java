package otpishAI.otpishAI_Backend.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import otpishAI.otpishAI_Backend.entity.Product;
import otpishAI.otpishAI_Backend.entity.ProductDetail;

import java.util.Date;
import java.util.List;

//상품 정보 엔티티
@Getter
@Setter
public class ProductDTO {

    //품번
    private String productCode;

    //상품명
    private String productName;

    private String productImg0;
    private String productImg1;
    private String productImg2;
    private String productImg3;
    private String productImg4;
    private String productImg5;

    //정가
    private Integer oPrice;
    //판매가
    private Integer rPrice;

    private String category1;
    private String category2;
    private String category3;
    //옷 분류(앞자리 3개로 대분류, 뒷자리 3개로 소분류)
    private String genreCode;

    //결재 건수
    private Integer payment;
    //찜 횟수
    private Integer favorite;
    //리뷰 수
    private Integer review;
    //등록일
    private Date productRdate;

    //상품 삭제 여부
    private Integer isDeleted;
    private String judge;
    private String productBrand;

    //상품의 대상 성별(1:유니섹스 2:여성 3:남성)
    private Integer productGender;

    private String productRegistrant;

    private Integer productNum;


    private List<ProductDetail> productDetails;

    public ProductDTO(Product product, List<ProductDetail> productDetails){
        super();
        productCode = product.getProductCode();

        productName = product.getProductName();

        productImg0 = product.getProductImg0();
        productImg1 = product.getProductImg1();
        productImg2 = product.getProductImg2();
        productImg3 = product.getProductImg3();
        productImg4 = product.getProductImg4();
        productImg5 = product.getProductImg5();

        oPrice = product.getOPrice();
        rPrice = product.getRPrice();

        category1 = product.getCategory1();
        category2 = product.getCategory2();
        category3 = product.getCategory3();

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
    }
}
