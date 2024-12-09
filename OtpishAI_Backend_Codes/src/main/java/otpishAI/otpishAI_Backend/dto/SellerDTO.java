
package otpishAI.otpishAI_Backend.dto;

import lombok.Data;
import otpishAI.otpishAI_Backend.entity.Seller;

@Data
public class SellerDTO {
    private String sellerId;
    private String sellerEmail;
    private String sellerPhone;
    private String sellerAddr;
    private String sellerPwd;
    private String sellerName;
    private String sellerBrandName;
    private String sellerImg;

    public SellerDTO(Seller seller) {
        this.sellerId = seller.getSellerId();
        this.sellerEmail = seller.getSellerEmail();
        this.sellerPhone = seller.getSellerPhone();
        this.sellerAddr = seller.getSellerAddr();
        this.sellerName = seller.getSellerName();
        this.sellerBrandName = seller.getSellerBrandName();
        this.sellerImg = seller.getSellerImg();
    }
}