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
import otpishAI.otpishAI_Backend.dto.ProductDTO;
import otpishAI.otpishAI_Backend.entity.Product;
import otpishAI.otpishAI_Backend.entity.ProductDetail;
import otpishAI.otpishAI_Backend.service.ProductService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@AllArgsConstructor
public class ProductPageController {

    private final ProductService productService;

    @GetMapping("/product")
    public ResponseEntity<?> listingProduct(@RequestParam(defaultValue = "", name = "genre") String genre,
                                                      @RequestParam(defaultValue = "", name = "brand") String brandIds,
                                                      @RequestParam(defaultValue = "", name = "category") String category,
                                                      @PageableDefault(page = 0, size = 30, sort = "payment", direction = Sort.Direction.DESC) Pageable pageable) {

        // 쉼표로 구분된 브랜드 ID 문자열을 문자열 리스트로 변환
        String[] brandArr = brandIds.split(",");
        ArrayList<String> brandList = new ArrayList<>(Arrays.asList(brandArr));
        //쉽표로 구분된 카테고리 문자열을 문자열 리스트로 변환
        String[] strArr = category.split(",");
        ArrayList<String> categoryList = new ArrayList<>(Arrays.asList(strArr));

        Page<Product> products = productService.productsSelectByUri(genre, brandList, categoryList, pageable);

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/product_detail/{productNum}")
    public ResponseEntity<?> detailProduct(@PathVariable("productNum") Integer productNum){

        Product product = productService.productSelectByProductNum(productNum);
        List<ProductDetail> productDetails = productService.productDetailsByProductNum(productNum);

        ProductDTO productDTO = new ProductDTO(product, productDetails);
        return new ResponseEntity<>(productDTO, HttpStatus.OK);
    }
}
