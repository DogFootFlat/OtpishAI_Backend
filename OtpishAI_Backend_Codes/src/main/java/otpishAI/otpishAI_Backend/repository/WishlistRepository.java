package otpishAI.otpishAI_Backend.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import otpishAI.otpishAI_Backend.entity.Wishlist;

import java.util.List;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {


   @Transactional
   @Modifying(clearAutomatically = true)
   @Query(value = "UPDATE wishlist w SET w.product_num = array_remove(w.product_num, :productNum) WHERE w.wish_owner = :wishOwner", nativeQuery = true)
   void removeProductNum(@Param("wishOwner") String wishOwner, @Param("productNum") Long productNum);

   @Modifying
   @Transactional
   @Query(value = "UPDATE wishlist w SET w.product_num = array_append(w.product_num, :productNum) WHERE w.wish_owner = :wishOwner", nativeQuery = true)
   void addProductNum(@Param("wishOwner") String wishOwner, @Param("productNum") Long productNum);

   @Query(value = "SELECT CASE WHEN array_position(w.product_num, :productNum) IS NOT NULL THEN TRUE ELSE FALSE END " +
           "FROM wishlist w WHERE w.wish_owner = :wishOwner", nativeQuery = true)
   Boolean existsProductNumInWishlist(@Param("wishOwner") String wishOwner, @Param("productNum") Long productNum);
   @Transactional
   @Modifying(clearAutomatically = true)
   void deleteByWishOwner(String wishOwner);

   Wishlist findByWishOwner(String wishOwner);



}