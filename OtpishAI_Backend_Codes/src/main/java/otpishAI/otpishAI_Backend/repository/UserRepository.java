package otpishAI.otpishAI_Backend.repository;

import otpishAI.otpishAI_Backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {


    User findByUsername(String username);

    Boolean existsByUsername(String username);
}