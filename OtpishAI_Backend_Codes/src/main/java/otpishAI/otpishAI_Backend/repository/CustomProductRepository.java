package otpishAI.otpishAI_Backend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import otpishAI.otpishAI_Backend.entity.Product;

import java.util.List;

public interface CustomProductRepository{

    public Page<Product> listingProduct(String genre, List<String> brand, List<String> category, Pageable pageable);


}