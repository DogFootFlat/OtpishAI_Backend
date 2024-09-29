package otpishAI.otpishAI_Backend.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import otpishAI.otpishAI_Backend.entity.Cart;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByUsernameAndDetailCode(String username, String detailCode);

    @Transactional
    @Modifying(clearAutomatically = true)
    void deleteByCartNum(Long cartNum);

    List<Cart> findAllByUsernameOrderByCartNum(String username);

}