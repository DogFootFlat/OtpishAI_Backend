package otpishAI.otpishAI_Backend.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import otpishAI.otpishAI_Backend.dto.ProductDTO;
import otpishAI.otpishAI_Backend.dto.ReviewDTO;
import otpishAI.otpishAI_Backend.dto.SellerDTO;
import otpishAI.otpishAI_Backend.entity.Product;
import otpishAI.otpishAI_Backend.jwt.JWTUtil;
import otpishAI.otpishAI_Backend.service.ProductService;
import otpishAI.otpishAI_Backend.service.RefreshTCheckService;
import otpishAI.otpishAI_Backend.service.SellerService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
public class SellerController {

    private final SellerService sellerService;

    private final RefreshTCheckService refreshTCheckService;

    private final JWTUtil jwtUtil;

    private final ProductService productService;
    // 판매자 회원가입
    @PostMapping("/seller/register")
    public ResponseEntity<String> sellerRegister(@RequestBody SellerDTO sellerDto,
                                                 @RequestParam(value = "sellerimg", required = false) MultipartFile sellerFile) {

        String imgPath = "";
        try {
            // 이미지 파일이 있는 경우에만 처리
            if (sellerFile != null) {
                try {
                    // 각 파일을 저장하고 반환된 경로를 리스트에 추가
                    String imagePath = sellerService.saveImage(sellerFile);
                    imgPath = imagePath;
                } catch (IOException e) {
                    e.printStackTrace();
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }

            }

            // ReviewDTO에 이미지 경로 리스트를 배열로 설정 (이미지가 없을 경우 빈 배열로 설정)
            sellerDto.setSellerImg(imgPath);

            boolean isRegistered = sellerService.registerSeller(sellerDto);
            if (isRegistered) {
                return ResponseEntity.ok("회원 가입 성공");
            } else {
                return ResponseEntity.badRequest().body("회원 정보가 이미 존재.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 판매자 로그인
    @PostMapping("/seller/login")
    public ResponseEntity<String> sellerLogin(@RequestBody SellerDTO sellerDto, HttpServletResponse response) {
        boolean isAuthenticated = sellerService.loginSeller(sellerDto, response);
        if (isAuthenticated) {
            return ResponseEntity.ok("로그인 성공");
        } else {
            return ResponseEntity.status(401).body("잘못된 로그인 정보");
        }
    }


    @PostMapping("/seller/product/add")
    public ResponseEntity<?> addReview(
            @RequestBody ProductDTO productDTO,
            @RequestParam(value = "productimg", required = false) MultipartFile[] productFiles,
            @RequestParam(value = "productinfo", required = false) MultipartFile[] productInfos,
            HttpServletRequest request,
            HttpServletResponse response) {

        String access = refreshTCheckService.getAccessT(request, response);
        String sellerId = jwtUtil.getUsername(access);

        if(!access.equals(""))
        {
            List<String> imagePaths = new ArrayList<>();
            List<String> infoPaths = new ArrayList<>();
            try {
                // 이미지 파일이 있는 경우에만 처리
                if (productFiles != null && productFiles.length > 0) {
                    for (MultipartFile productimg : productFiles) {
                        try {
                            // 각 파일을 저장하고 반환된 경로를 리스트에 추가
                            String imagePath = productService.saveImages(productimg);
                            imagePaths.add(imagePath);
                        } catch (IOException e) {
                            e.printStackTrace();
                            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                        }
                    }
                }

                // ReviewDTO에 이미지 경로 리스트를 배열로 설정 (이미지가 없을 경우 빈 배열로 설정)
                productDTO.setProductImg(imagePaths.toArray(new String[0]));

                // 이미지 파일이 있는 경우에만 처리
                if (productInfos != null && productInfos.length > 0) {
                    for (MultipartFile productinfo : productInfos) {
                        try {
                            // 각 파일을 저장하고 반환된 경로를 리스트에 추가
                            String infoPath = productService.saveInfos(productinfo);
                            infoPaths.add(infoPath);
                        } catch (IOException e) {
                            e.printStackTrace();
                            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                        }
                    }
                }

                // ReviewDTO에 이미지 경로 리스트를 배열로 설정 (이미지가 없을 경우 빈 배열로 설정)
                productDTO.setProductInfo(infoPaths.toArray(new String[0]));

                if (productService.saveProduct(productDTO, sellerId)) {
                    return new ResponseEntity<>(HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }


    @PutMapping("/seller/product/update/{productNum}")
    public ResponseEntity<?> updateReview(
            @PathVariable("productNum") Long productNum,
            @RequestBody ProductDTO productDTO,
            @RequestParam(value = "reviewimg", required = false) MultipartFile[] reviewimgFiles,
            @RequestParam(value = "productinfo", required = false) MultipartFile[] productInfos,
            HttpServletRequest request,
            HttpServletResponse response) {

        String access = refreshTCheckService.getAccessT(request, response);

        if(!access.equals(""))
        {
            List<String> imagePaths = new ArrayList<>();
            List<String> infoPaths = new ArrayList<>();
            try {
                // 이미지 파일이 있는 경우에만 처리
                if (reviewimgFiles != null && reviewimgFiles.length > 0) {
                    for (MultipartFile reviewimg : reviewimgFiles) {
                        try {
                            String imagePath = productService.saveImages(reviewimg);
                            imagePaths.add(imagePath);
                        } catch (IOException e) {
                            e.printStackTrace();
                            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                        }
                    }
                    productDTO.setProductImg(imagePaths.toArray(new String[0])); // 새 이미지 경로로 설정
                }
                // 이미지를 전송하지 않은 경우, reviewDTO의 이미지 필드를 그대로 두어 기존 데이터를 유지

                // 이미지 파일이 있는 경우에만 처리
                if (productInfos != null && productInfos.length > 0) {
                    for (MultipartFile productinfo : productInfos) {
                        try {
                            // 각 파일을 저장하고 반환된 경로를 리스트에 추가
                            String infoPath = productService.saveInfos(productinfo);
                            infoPaths.add(infoPath);
                        } catch (IOException e) {
                            e.printStackTrace();
                            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                        }
                    }
                    productDTO.setProductInfo(infoPaths.toArray(new String[0]));
                }


                // 리뷰 업데이트 처리
                if (productService.updateProduct(productDTO, productNum)) {
                    return new ResponseEntity<>(HttpStatus.OK);
                }
                else {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping("/seller/product/delete/{productNum}")
    public ResponseEntity<?> deleteProduct(@PathVariable("productNum") Long productNum,HttpServletRequest request,
                                           HttpServletResponse response){

        String access = refreshTCheckService.getAccessT(request, response);
        if(!access.equals(""))
        {
            productService.deleteProduct(productNum);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/seller/product/list")
    public ResponseEntity<?> productLists(HttpServletRequest request, HttpServletResponse response){
        String access = refreshTCheckService.getAccessT(request, response);
        if(!access.equals(""))
        {
            if(productService.productLists(jwtUtil.getUsername(access)) != null){
                Product[] products = productService.productLists(jwtUtil.getUsername(access));
                return new ResponseEntity<>(products, HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }


    @GetMapping("/seller/product/list/detail")
    public ResponseEntity<?> productListsDetail(HttpServletRequest request, HttpServletResponse response){
        String access = refreshTCheckService.getAccessT(request, response);
        if(!access.equals(""))
        {
            if(productService.productLists(jwtUtil.getUsername(access)) != null){
                ProductDTO[] productDTOS = productService.productDTOLists(jwtUtil.getUsername(access));
                return new ResponseEntity<>(productDTOS, HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/seller/update/profile")
    public ResponseEntity<?> sellerProfileUpdate(HttpServletRequest request, HttpServletResponse response){
        String access = refreshTCheckService.getAccessT(request, response);
        if(!access.equals(""))
        {
            SellerDTO sellerDTO = sellerService.getSellerProfile(jwtUtil.getUsername(access));
            return new ResponseEntity<>(sellerDTO, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }


    @PostMapping("/seller/update/profile")
    public ResponseEntity<String> sellerProfileUpdate(@RequestBody SellerDTO sellerDto,
                                                      @RequestParam(value = "sellerimg", required = false) MultipartFile sellerFile) {
        String imgPath = "";
        try {
            // 이미지 파일이 있는 경우에만 처리
            if (sellerFile != null) {
                try {
                    // 각 파일을 저장하고 반환된 경로를 리스트에 추가
                    String imagePath = sellerService.saveImage(sellerFile);
                    imgPath = imagePath;
                } catch (IOException e) {
                    e.printStackTrace();
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }

            }

            // ReviewDTO에 이미지 경로 리스트를 배열로 설정 (이미지가 없을 경우 빈 배열로 설정)
            sellerDto.setSellerImg(imgPath);

            boolean isRegistered = sellerService.updateSeller(sellerDto);
            if (isRegistered) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
