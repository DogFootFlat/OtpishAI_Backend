package otpishAI.otpishAI_Backend.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import otpishAI.otpishAI_Backend.entity.Orders;

import java.util.List;

public interface OrdersRepository extends JpaRepository<Orders, Long> {

   List<Orders> findAllByOrderOwner(String orderOwner);

   Orders findByOrderNum(Long orderNum);

}