package otpishAI.otpishAI_Backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="customers_addr")
public class CustomersAddr {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="addr_num ")
    private Long addrNum;

    @Column(name="addr_owner")
    private String addrOwner;

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name="addr")
    private String[] addrs;


    @Column(name="main_addr")
    private String mainAddr;

}