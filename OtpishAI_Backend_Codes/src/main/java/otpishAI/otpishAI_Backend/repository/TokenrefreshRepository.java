package otpishAI.otpishAI_Backend.repository;

import org.springframework.data.jpa.repository.Modifying;
import otpishAI.otpishAI_Backend.entity.Tokenrefresh;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenrefreshRepository extends JpaRepository<Tokenrefresh, Long> {

    Boolean existsByRefresh(String refresh);

    Boolean existsByUsername(String username);

    Optional<Tokenrefresh> findByUsername(String username);

    @Transactional
    @Modifying(clearAutomatically = true)
    void deleteByUsername(String username);
}
