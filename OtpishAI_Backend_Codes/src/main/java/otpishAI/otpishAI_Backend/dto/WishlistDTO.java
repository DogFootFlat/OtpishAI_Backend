package otpishAI.otpishAI_Backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import otpishAI.otpishAI_Backend.entity.Product;
import otpishAI.otpishAI_Backend.entity.Wishlist;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WishlistDTO {

    //관심 상품 번호(인덱싱용)
    private Long wishNum;

    //관심 상품 주인
    private String wishOwner;

    //관심 상품 번호 배열
    private Long[] productNum;

    //관심 상품 정보(관심 상품 번호와 1:1 매칭)
    private List<Product> products;

    public WishlistDTO(Wishlist wishlist, List<Product> products){
        wishNum = wishlist.getWishNum();
        wishOwner = wishlist.getWishOwner();
        productNum = Arrays.copyOf(wishlist.getProductNum(), wishlist.getProductNum().length);
        this.products = products;
    }
}