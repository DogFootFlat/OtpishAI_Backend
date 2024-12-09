package otpishAI.otpishAI_Backend.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import otpishAI.otpishAI_Backend.entity.CustomersAddr;

public interface CustomersAddrRepository extends JpaRepository<CustomersAddr, Long> {


   @Modifying
   @Transactional
   @Query(value = "UPDATE customers_addr c SET c.addr = array_append(c.addr, :addrs) WHERE c.addr_owner = :addrOwner", nativeQuery = true)
   void addAddrs(@Param("addrOwner") String addrOwner, @Param("addrs") String addrs);
   @Transactional
   @Modifying(clearAutomatically = true)
   @Query(value = "UPDATE customers_addr c " +
           "SET c.addr = array_remove(c.addr, :addrs) " +
           "WHERE c.addr_owner = :addrOwner", nativeQuery = true)
   void removeAddrs(@Param("addrOwner") String addrOwner, @Param("addrs") String address);
   @Query(value = "SELECT CASE WHEN array_position(c.addr, :addrs) IS NOT NULL THEN TRUE ELSE FALSE END " +
           "FROM customers_addr c WHERE c.addr_owner = :addrOwner", nativeQuery = true)
   Boolean existsAddrsInCustomersAddr(@Param("addrOwner") String addrOwner, @Param("addrs") String addrs);

   CustomersAddr findByAddrOwner(String addrOwner);



}