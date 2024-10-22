package otpishAI.otpishAI_Backend.service;

import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import otpishAI.otpishAI_Backend.dto.CartDTO;
import otpishAI.otpishAI_Backend.dto.CartResponseDTO;
import otpishAI.otpishAI_Backend.entity.Cart;
import otpishAI.otpishAI_Backend.entity.ProductDetail;
import otpishAI.otpishAI_Backend.repository.CartRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CartService {
    private final CartRepository cartRepository;

    public void addOrUpdateCart(String username, ProductDetail productDetail){
        Optional<Cart> existCart = cartRepository.findByUsernameAndDetailCode(username, productDetail.getDetailCode());

        if(existCart.isPresent()){
            Cart updateCart = existCart.get();
            updateCart.setTotalPrice(updateCart.getTotalPrice() + updateCart.getRPrice());
            updateCart.setQuantity(updateCart.getQuantity() + 1L);
            cartRepository.save(updateCart);
        } else {
            Cart newCart= new Cart();
            newCart.setUsername(username);
            newCart.setDetailCode(productDetail.getDetailCode());
            newCart.setOPrice(productDetail.getOPrice());
            newCart.setRPrice(productDetail.getRPrice());
            newCart.setTotalPrice(productDetail.getRPrice());
            newCart.setQuantity(1L);
            newCart.setProductCode(productDetail.getProductCode());
            newCart.setProductNum(productDetail.getProductNum());
            cartRepository.save(newCart);
        }
    }

    public Boolean minusCart(Long cartNum){
        try{
            Cart cart = cartRepository.findByCartNum(cartNum);
            if(cart == null){
                return false;
            }
            if(cart.getQuantity() <= 1) {
                cartRepository.deleteByCartNum(cartNum);
            }
            else{
                cart.setTotalPrice(cart.getTotalPrice() - cart.getRPrice());
                cart.setQuantity(cart.getQuantity() - 1L);
                cartRepository.save(cart);
            }
            return true;
        }catch (DataAccessException e){
            return false;
        }
    }


    public Boolean deleteCart(Long cartNum){
        try{
            Cart cart = cartRepository.findByCartNum(cartNum);
            if(cart == null){
                return false;
            }
            cartRepository.deleteByCartNum(cartNum);
            return true;
        }catch (DataAccessException e){
            return false;
        }
    }


    public Boolean plusCart(Long cartNum){
       try{
            Cart cart = cartRepository.findByCartNum(cartNum);
            if(cart == null){
                return false;
            }
            cart.setTotalPrice(cart.getTotalPrice() + cart.getRPrice());
            cart.setQuantity(cart.getQuantity() + 1L);
            cartRepository.save(cart);
            return true;
        }catch (DataAccessException e){
            return false;
        }
    }

    public CartResponseDTO cartsByUsername(String username){

        List<Cart> cartList = cartRepository.findAllByUsernameOrderByCartNum(username);
        List<CartDTO> cartDTOS = cartList.stream()
                .map(cart -> new CartDTO(cart))
                .collect(Collectors.toList());
        CartResponseDTO cartResponseDTO = new CartResponseDTO();
        cartResponseDTO.setCartDTOS(cartDTOS);
        cartResponseDTO.setCartCNT(cartDTOS.size());

        return cartResponseDTO;
    }

    public Cart cartFind(String username, String detailCode){
        return cartRepository.findByDetailCodeAndUsername(detailCode, username);
    }




}
