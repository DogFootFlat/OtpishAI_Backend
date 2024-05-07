package otpishAI.otpishAI_Backend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import otpishAI.otpishAI_Backend.entity.Product;

public interface ProductRepository extends JpaRepository<Product, String> {

    Page<Product> findAllOrderByPayment_NDesc(Pageable pageable);


}