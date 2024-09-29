package otpishAI.otpishAI_Backend.service;

import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import otpishAI.otpishAI_Backend.entity.Cart;
import otpishAI.otpishAI_Backend.entity.ProductDetail;
import otpishAI.otpishAI_Backend.entity.Review;
import otpishAI.otpishAI_Backend.repository.CartRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CartService {
    private final CartRepository cartRepository;

    public void updateCart(String username, ProductDetail productDetail){
        Optional<Cart> existCart = cartRepository.findByUsernameAndDetailCode(username, productDetail.getDetailCode());

        if(existCart.isPresent()){
            Cart updateCart = existCart.get();
            updateCart.setQuantity(updateCart.getQuantity() + 1);
            cartRepository.save(updateCart);
        } else {
            Cart newCart= new Cart();
            newCart.setUsername(username);
            newCart.setDetailCode(productDetail.getDetailCode());
            newCart.setOPrice(productDetail.getOPrice());
            newCart.setRPrice(productDetail.getRPrice());
            newCart.setQuantity((long)1);
            cartRepository.save(newCart);
        }
    }


    public Boolean deleteCart(Long cartNum){
        try{
            cartRepository.deleteByCartNum(cartNum);
            return true;
        }catch (DataAccessException e){
            return false;
        }
    }

    public List<Cart> cartssByUsername(String username){

        return cartRepository.findAllByUsernameOrderByCartNum(username);
    }


}
