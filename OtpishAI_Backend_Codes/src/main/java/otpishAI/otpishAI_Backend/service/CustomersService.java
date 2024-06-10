package otpishAI.otpishAI_Backend.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import otpishAI.otpishAI_Backend.dto.CustomersDTO;
import otpishAI.otpishAI_Backend.dto.OAuth2_CustomersDTO;
import otpishAI.otpishAI_Backend.entity.Customers;
import otpishAI.otpishAI_Backend.repository.CustomersRepository;

@Service
@AllArgsConstructor
public class CustomersService {

    private final CustomersRepository customersRepository;

    public CustomersDTO responseUser(String username){
        Customers customers = customersRepository.findByUsername(username);
        CustomersDTO customersDTO = new CustomersDTO(customers);

        return customersDTO;
    }

    public void saveUser(CustomersDTO customers){
        Customers customer = new Customers();
        customer.setEmail(customers.getEmail());
        customer.setAddr(customers.getAddr());
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
        customersRepository.save(customer);
    }
}
