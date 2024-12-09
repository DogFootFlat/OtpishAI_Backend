
package otpishAI.otpishAI_Backend.repository;

import otpishAI.otpishAI_Backend.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerRepository extends JpaRepository<Seller, String> {
    Seller findBySellerId(String sellerId);
}