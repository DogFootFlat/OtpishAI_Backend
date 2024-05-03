package otpishAI.otpishAI_Backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import otpishAI.otpishAI_Backend.entity.Product;
import otpishAI.otpishAI_Backend.entity.User;

import java.util.ArrayList;

public interface ProductRepository extends JpaRepository<Product, String> {

    ArrayList<Product> findAllOrderByProduct_R_date();
}