package otpishAI.otpishAI_Backend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import otpishAI.otpishAI_Backend.entity.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String> {

    Page<Product> findAllByOrderByPaymentDesc(Pageable pageable);
    Product findByProductNum(Long productNum);
    @Query(value = "SELECT * FROM product p WHERE p.product_num IN :productNums", nativeQuery = true)
    List<Product> findProductsByProductNums(@Param("productNums") Long[] productNums);

}