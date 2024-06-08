package otpishAI.otpishAI_Backend.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import otpishAI.otpishAI_Backend.entity.Product;
import otpishAI.otpishAI_Backend.entity.ProductDetail;
import otpishAI.otpishAI_Backend.repository.CustomProductRepositoryImpl;
import otpishAI.otpishAI_Backend.repository.ProductDetailRepository;
import otpishAI.otpishAI_Backend.repository.ProductRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final CustomProductRepositoryImpl customProductRepository;

    private final ProductDetailRepository productDetailRepository;

    public Page<Product> productSelectByPaymentN(Pageable pageable){
            return  productRepository.findAllByOrderByPaymentDesc(pageable);
    }

    public Page<Product> productsSelectByUri(String genre, List<String> brand, List<String> category, Pageable pageable){

        return customProductRepository.listingProduct(genre, brand, category, pageable);
    }


    public Product productSelectByProductNum(Long productNum){

        return productRepository.findByProductNum(productNum);
    }
    public List<ProductDetail> productDetailsByProductNum(Long productNum){
        return  productDetailRepository.findAllByProductNum(productNum);

    }
}
