package otpishAI.otpishAI_Backend.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import otpishAI.otpishAI_Backend.entity.Product;
import otpishAI.otpishAI_Backend.repository.CustomProductRepositoryImpl;
import otpishAI.otpishAI_Backend.repository.ProductRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final CustomProductRepositoryImpl customProductRepository;

    public Page<Product> productSelectByPaymentN(Pageable pageable){
            return  productRepository.findAllOrderByPayment_NDesc(pageable);
    }

    public Page<Product> productSelectByUri(String genre, List<Integer> brand, List<String> category, Pageable pageable){

        return customProductRepository.listingProduct(genre, brand, category, pageable);
    }

}
