package otpishAI.otpishAI_Backend.service;

import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import otpishAI.otpishAI_Backend.dto.ProductDTO;
import otpishAI.otpishAI_Backend.dto.ReviewDTO;
import otpishAI.otpishAI_Backend.entity.Product;
import otpishAI.otpishAI_Backend.entity.ProductDetail;
import otpishAI.otpishAI_Backend.entity.Review;
import otpishAI.otpishAI_Backend.repository.CustomProductRepositoryImpl;
import otpishAI.otpishAI_Backend.repository.ProductDetailRepository;
import otpishAI.otpishAI_Backend.repository.ProductRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final CustomProductRepositoryImpl customProductRepository;

    private final ProductDetailRepository productDetailRepository;


    private final String IMAGE_UPLOAD_DIR = "products/img/"; // 이미지 저장 폴더 경로 상수 추가

    private final String INFO_UPLOAD_DIR = "products/info/"; // 설명 이미지 저장 폴더 경로 상수 추가
    public Page<Product> productSelectByPaymentN(Pageable pageable){
            return  productRepository.findAllByOrderByPaymentDesc(pageable);
    }

    public Page<Product> productsSelectByUri(String genre, List<String> brand, List<String> category, Pageable pageable){

        return customProductRepository.listingProduct(genre, brand, category,"", "", "", pageable, false);
    }

    public Page<Product> productSearchtByKeyword(String genre, String keyword, Pageable pageable){
        ArrayList<String> brandList = new ArrayList<>();
        brandList.add(keyword);
        ArrayList<String> categoryList = new ArrayList<>();
        categoryList.add(keyword);
        return customProductRepository.listingProduct(genre, brandList, categoryList,keyword, keyword, keyword, pageable, true);
    }


    public Product productSelectByProductNum(Long productNum){

        return productRepository.findByProductNum(productNum);
    }
    public List<ProductDetail> productDetailsByProductNum(Long productNum){
        return  productDetailRepository.findAllByProductNum(productNum);

    }

    public ProductDetail productDetailByDetailCode(String detailCode){
        return productDetailRepository.findByDetailCode(detailCode);
    }

    public ProductDetail productDetailByCode(String productCode){
        return productDetailRepository.findFirstByProductCode(productCode);
    }

    public Boolean saveProduct(ProductDTO productDTO, String sellerId){
        try {
            Product product = new Product();
            product.setProductBrand(productDTO.getProductBrand());
            product.setProductCode(productDTO.getProductCode());
            product.setProductGender(productDTO.getProductGender());
            product.setProductImg(productDTO.getProductImg());
            product.setProductInfo(productDTO.getProductInfo());
            product.setProductName(productDTO.getProductName());
            product.setProductRdate(LocalDateTime.now());
            product.setCategory(productDTO.getCategory());
            product.setFavorite(0L);
            product.setGenreCode(productDTO.getGenreCode());
            product.setIsDeleted(0);
            product.setRPrice(productDTO.getRPrice());
            product.setJudge(0L);
            product.setOPrice(productDTO.getOPrice());
            product.setProductRegistrant(sellerId);
            product.setPayment(0L);
            product.setReview(0L);
            productRepository.save(product);

            ProductDetail[] productDetails = productDTO.getProductDetail();
            for(ProductDetail productDetail : productDetails){
                productDetail.setProductNum(productDTO.getProductNum());
                productDetail.setProductCode(productDTO.getProductCode());
                productDetailRepository.save(productDetail);
            }
            return true;
        } catch (DataAccessException e) {
            System.out.println("Error Log : " + e);
            return false;
        }
    }
    public String saveImages(MultipartFile productImg) throws IOException {
        try {
            // 이미지 저장 폴더 생성
            File uploadDir = new File(IMAGE_UPLOAD_DIR);
            if (!uploadDir.exists()) {
                if (!uploadDir.mkdirs()) {
                    throw new IOException("Could not create directory: " + IMAGE_UPLOAD_DIR);
                }
            }

            // 쓰기 권한 확인
            if (!uploadDir.canWrite()) {
                throw new IOException("No write permission for directory: " + IMAGE_UPLOAD_DIR);
            }

            // 파일 경로 설정
            String fileName = System.currentTimeMillis() + "_" + productImg.getOriginalFilename();
            String filePath = IMAGE_UPLOAD_DIR + fileName;

            // 이미지 파일 저장
            Files.copy(productImg.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);

            return filePath; // 저장된 파일 경로 반환
        } catch (IOException e) {
            // 오류 로그 출력 및 예외 처리
            System.err.println("Error saving image: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public Boolean updateProduct(ProductDTO productDTO, Long productNum){
        Product isExist = productRepository.findByProductNum(productNum);
        if(isExist != null){
            isExist.setProductBrand(productDTO.getProductBrand());
            isExist.setProductGender(productDTO.getProductGender());
            isExist.setProductImg(productDTO.getProductImg());
            isExist.setProductInfo(productDTO.getProductInfo());
            isExist.setProductName(productDTO.getProductName());
            isExist.setCategory(productDTO.getCategory());
            isExist.setRPrice(productDTO.getRPrice());
            isExist.setOPrice(productDTO.getOPrice());
            productRepository.save(isExist);

            ProductDetail[] productDetails = productDTO.getProductDetail();
            productDetailRepository.deleteByProductNum(productNum);
            for(ProductDetail productDetail : productDetails){
                productDetail.setProductNum(productDTO.getProductNum());
                productDetail.setProductCode(productDTO.getProductCode());
                productDetailRepository.save(productDetail);
            }
        }
        try {

            return true;
        } catch (DataAccessException e) {
            System.out.println("Error Log : " + e);
            return false;
        }
    }

    public Boolean deleteProduct(Long productNum){
        try {
            Product isExist = productRepository.findByProductNum(productNum);
            if(isExist != null){
                isExist.setIsDeleted(1);
                productRepository.save(isExist);
            }
            else{
                return false;
            }
            productDetailRepository.deleteByProductNum(productNum);
            return true;
        } catch (DataAccessException e) {
            System.out.println("Error Log : " + e);
            return false;
        }
    }

    public Product[] productLists(String sellerId){
        Product[] products = productRepository.findAllByProductRegistrant(sellerId);
        if (products != null && products.length > 0) {
            return products;
        } else {
            return null;
        }
    }


    public ProductDTO[] productDTOLists(String sellerId){
        Product[] products = productRepository.findAllByProductRegistrant(sellerId);
        if (products != null && products.length > 0) {
            ProductDTO[] productDTOS = Arrays.stream(products).map(product -> {
                List<ProductDetail> productDetails = productDetailRepository.findAllByProductNum(product.getProductNum());
                return new ProductDTO(product, productDetails);
            }).toArray(ProductDTO[]::new);
            return productDTOS;
        } else {
            return null;
        }
    }

    public String saveInfos(MultipartFile productInfos) throws IOException {
        try {
            // 이미지 저장 폴더 생성
            File uploadDir = new File(INFO_UPLOAD_DIR);
            if (!uploadDir.exists()) {
                if (!uploadDir.mkdirs()) {
                    throw new IOException("Could not create directory: " + INFO_UPLOAD_DIR);
                }
            }

            // 쓰기 권한 확인
            if (!uploadDir.canWrite()) {
                throw new IOException("No write permission for directory: " + INFO_UPLOAD_DIR);
            }

            // 파일 경로 설정
            String fileName = System.currentTimeMillis() + "_" + productInfos.getOriginalFilename();
            String filePath = INFO_UPLOAD_DIR + fileName;

            // 이미지 파일 저장
            Files.copy(productInfos.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);

            return filePath; // 저장된 파일 경로 반환
        } catch (IOException e) {
            // 오류 로그 출력 및 예외 처리
            System.err.println("Error saving image: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

}
