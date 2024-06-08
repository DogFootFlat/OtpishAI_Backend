package otpishAI.otpishAI_Backend.repository;

import otpishAI.otpishAI_Backend.entity.Customers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomersRepository extends JpaRepository<Customers, String> {


    Customers findByUsername(String username);

    Boolean existsByUsername(String username);
}