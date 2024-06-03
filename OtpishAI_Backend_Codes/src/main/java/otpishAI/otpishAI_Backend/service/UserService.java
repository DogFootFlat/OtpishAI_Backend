package otpishAI.otpishAI_Backend.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import otpishAI.otpishAI_Backend.dto.UserDTO;
import otpishAI.otpishAI_Backend.entity.Customers;
import otpishAI.otpishAI_Backend.repository.UserRepository;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserDTO responseUser(String username){
        Customers customers = userRepository.findByUsername(username);
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(customers.getEmail());
        userDTO.setRole(customers.getRole());
        userDTO.setName(customers.getName());
        userDTO.setUsername(customers.getUsername());

        return userDTO;
    }

    public void saveUser(Customers customers){
        userRepository.save(customers);
    }
}
