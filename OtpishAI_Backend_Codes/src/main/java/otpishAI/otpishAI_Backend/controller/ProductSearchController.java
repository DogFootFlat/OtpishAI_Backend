package otpishAI.otpishAI_Backend.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import otpishAI.otpishAI_Backend.dto.ProductDetailDTO;
import otpishAI.otpishAI_Backend.entity.Product;
import otpishAI.otpishAI_Backend.entity.ProductDetail;
import otpishAI.otpishAI_Backend.entity.Review;
import otpishAI.otpishAI_Backend.service.ProductService;
import otpishAI.otpishAI_Backend.service.ReviewService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@AllArgsConstructor
public class ProductSearchController {

    private final ProductService productService;

    @GetMapping("/search")
    public ResponseEntity<Page<Product>> listingProduct(@RequestParam(defaultValue = "", name = "genre") String genre,
                                                      @RequestParam(defaultValue = "", name = "keyword") String keyword,
                                                      @PageableDefault(page = 0, size = 30, sort = "payment_n", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Product> products = productService.productSearchtByKeyword(genre, keyword, pageable);

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

}
