package otpishAI.otpishAI_Backend.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import otpishAI.otpishAI_Backend.entity.Cart;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {

    private Long cartNum;

    private String username;

    private String detailCode;

    private Long oPrice;

    private Long rPrice;

    private int quantity;

    public CartDTO (Cart cart){

        super();

        cartNum = cart.getCartNum();
        username = cart.getUsername();
        detailCode = cart.getDetailCode();
        oPrice = cart.getOPrice();
        rPrice = cart.getRPrice();
        quantity = cart.getQuantity().intValue();

    }

}