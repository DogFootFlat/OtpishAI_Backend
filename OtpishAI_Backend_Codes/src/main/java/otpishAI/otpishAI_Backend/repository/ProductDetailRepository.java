package otpishAI.otpishAI_Backend.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import otpishAI.otpishAI_Backend.entity.ProductDetail;

import java.util.List;

public interface ProductDetailRepository extends JpaRepository<ProductDetail, String> {
    List<ProductDetail> findAllByProductNum(Long productNum);

    ProductDetail findByDetailCode(String detailCode);

    ProductDetail findFirstByProductNum(Long productNum);

    ProductDetail findFirstByProductCode(String productCode);

    List<ProductDetail> findAllByDetailCodeIn(String[] detailCodes);


    @Transactional
    @Modifying(clearAutomatically = true)
    void deleteByProductNum(Long productNum);

}