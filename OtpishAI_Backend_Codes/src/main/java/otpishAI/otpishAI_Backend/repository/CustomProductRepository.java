package otpishAI.otpishAI_Backend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import otpishAI.otpishAI_Backend.entity.Product;

import java.util.List;

public interface CustomProductRepository{

    Page<Product> listingProduct(String genre, List<String> brand, List<String> category, String productName,String productCode, String productRegistrant ,Pageable pageable, Boolean isSearch);


}