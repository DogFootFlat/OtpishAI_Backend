package otpishAI.otpishAI_Backend.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import otpishAI.otpishAI_Backend.dto.WishlistDTO;
import otpishAI.otpishAI_Backend.entity.Product;
import otpishAI.otpishAI_Backend.entity.Wishlist;
import otpishAI.otpishAI_Backend.repository.ProductRepository;
import otpishAI.otpishAI_Backend.repository.WishlistRepository;

import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class WishlistService {
    private final WishlistRepository wishlistRepository;
    private final ProductRepository productRepository;

    public WishlistDTO addOrDeleteWishlist(String username, Long productNum){

        Wishlist wishlist = wishlistRepository.findByWishOwner(username);
        if(wishlist == null){
            Long[] productNums = {productNum};
            Wishlist newWishlist = new Wishlist();
            newWishlist.setWishOwner(username);
            newWishlist.setProductNum(productNums);
            wishlistRepository.save(newWishlist);
            List<Product> products = productRepository.findProductsByProductNums(productNums);
            WishlistDTO wishlistDTO = new WishlistDTO(newWishlist, products);
            return wishlistDTO;
        }
        else{
            Boolean exist = wishlistRepository.existsProductNumInWishlist(username, productNum);
            if(exist){
                wishlistRepository.removeProductNum(username, productNum);
                Wishlist updatedWishlist = wishlistRepository.findByWishOwner(username);
                if(updatedWishlist.getProductNum().length == 0 || updatedWishlist.getProductNum() == null){
                    wishlistRepository.deleteByWishOwner(username);
                    return new WishlistDTO();
                }
                List<Product> products = productRepository.findProductsByProductNums(updatedWishlist.getProductNum());
                WishlistDTO wishlistDTO = new WishlistDTO(updatedWishlist, products);
                return wishlistDTO;
            }
            else{
                wishlistRepository.addProductNum(username, productNum);
                Wishlist updatedWishlist = wishlistRepository.findByWishOwner(username);
                List<Product> products = productRepository.findProductsByProductNums(updatedWishlist.getProductNum());
                WishlistDTO wishlistDTO = new WishlistDTO(updatedWishlist, products);
                return wishlistDTO;
            }
        }
    }

    public WishlistDTO wishlistList(String username){
        Wishlist wishlist = wishlistRepository.findByWishOwner(username);
        if(wishlist == null){
            return new WishlistDTO();
        }
        else{
            List<Product> products = productRepository.findProductsByProductNums(wishlist.getProductNum());
            WishlistDTO wishlistDTO = new WishlistDTO(wishlist, products);
            return wishlistDTO;
        }
    }
}
