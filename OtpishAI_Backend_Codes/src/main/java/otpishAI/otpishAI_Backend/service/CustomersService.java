package otpishAI.otpishAI_Backend.service;

import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import otpishAI.otpishAI_Backend.dto.CustomersDTO;
import otpishAI.otpishAI_Backend.dto.OAuth2_CustomersDTO;
import otpishAI.otpishAI_Backend.entity.Customers;
import otpishAI.otpishAI_Backend.entity.CustomersAddr;
import otpishAI.otpishAI_Backend.repository.CustomersAddrRepository;
import otpishAI.otpishAI_Backend.repository.CustomersRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class CustomersService {

    private final String IMAGE_UPLOAD_DIR = "customers/img/"; // 이미지 저장 폴더 경로 상수 추가
    private final CustomersRepository customersRepository;
    private final CustomersAddrRepository customersAddrRepository;

    public CustomersDTO responseUser(String username){

        Customers customers = customersRepository.findByUsername(username);
        CustomersAddr customersAddr = customersAddrRepository.findByAddrOwner(username);
        CustomersDTO customersDTO = new CustomersDTO(customers, customersAddr);

        return customersDTO;
    }

    public Boolean saveUser(CustomersDTO customers){
        try{
            Customers customer = new Customers();
            customer.setEmail(customers.getEmail());
            customer.setName(customers.getName());
            customer.setRole(customers.getRole());
            customer.setAge(customers.getAge());
            customer.setUsername(customers.getUsername());
            customer.setGender(customers.getGender());
            customer.setBirth(customers.getBirth());
            customer.setPhone(customers.getPhone());
            customer.setNickname(customers.getNickname());
            customer.setProfile_img(customers.getProfile_img());
            customer.setPreferGenre(customers.getPreferGenre());
            customer.setRegisterDate(LocalDateTime.now());
            customersRepository.save(customer);

            return true;

        } catch (DataAccessException e){
            return false;
        }
    }
    public String saveImage(MultipartFile customersImg) throws IOException {
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
            String fileName = System.currentTimeMillis() + "_" + customersImg.getOriginalFilename();
            String filePath = IMAGE_UPLOAD_DIR + fileName;

            // 이미지 파일 저장
            Files.copy(customersImg.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);

            return filePath; // 저장된 파일 경로 반환
        } catch (IOException e) {
            // 오류 로그 출력 및 예외 처리
            System.err.println("Error saving image: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public Boolean updateUser(CustomersDTO customers){
        try{

            Customers customer = customersRepository.findByUsername(customers.getUsername());
            if(customer != null) {
                customer.setName(customers.getName());
                customer.setUsername(customers.getUsername());
                customer.setGender(customers.getGender());
                customer.setPhone(customers.getPhone());
                customer.setNickname(customers.getNickname());
                customer.setProfile_img(customers.getProfile_img());
                customer.setPreferGenre(customers.getPreferGenre());
                customersRepository.save(customer);
                return true;
            }
            else{
                return false;
            }
        } catch (DataAccessException e){
            return false;
        }
    }
}
