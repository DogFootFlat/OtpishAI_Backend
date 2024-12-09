package otpishAI.otpishAI_Backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import otpishAI.otpishAI_Backend.entity.CustomersAddr;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomersAddrDTO {

    //인덱싱용
    private Long addrNum;
    //주소 목록 주인(email 아니고 username 입니다.)
    private String addrOwner;

    //주소 목록
    private String[] addrs;

    //대표 주소
    private String mainAddr;

    public CustomersAddrDTO(CustomersAddr customersAddr){
        this.addrNum = customersAddr.getAddrNum();
        this.addrOwner = customersAddr.getAddrOwner();
        this.addrs = customersAddr.getAddrs();
        this.mainAddr = customersAddr.getMainAddr();
    }

}