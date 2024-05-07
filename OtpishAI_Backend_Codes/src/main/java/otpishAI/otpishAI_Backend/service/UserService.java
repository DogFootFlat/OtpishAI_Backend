package otpishAI.otpishAI_Backend.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import otpishAI.otpishAI_Backend.dto.UserDTO;
import otpishAI.otpishAI_Backend.entity.User;
import otpishAI.otpishAI_Backend.repository.UserRepository;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserDTO responseUser(String username){
        User user = userRepository.findByUsername(username);
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(user.getEmail());
        userDTO.setRole(user.getRole());
        userDTO.setName(user.getName());
        userDTO.setUsername(user.getUsername());

        return userDTO;
    }

    public void saveUser(User user){
        userRepository.save(user);
    }
}
