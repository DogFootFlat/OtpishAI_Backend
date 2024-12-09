package otpishAI.otpishAI_Backend.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import otpishAI.otpishAI_Backend.dto.CustomersAddrDTO;
import otpishAI.otpishAI_Backend.entity.CustomersAddr;
import otpishAI.otpishAI_Backend.entity.Wishlist;
import otpishAI.otpishAI_Backend.repository.CustomersAddrRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class CustomersAddrService {
    CustomersAddrRepository customersAddrRepository;

    public CustomersAddrDTO addCustomersAddr(String username, String addrs){

        CustomersAddr customersAddr = customersAddrRepository.findByAddrOwner(username);
        //등록된 주소가 하나도 없다면 자동으로 메인 주소로 설정
        if(!customersAddrRepository.existsAddrsInCustomersAddr(username, addrs)){
            customersAddr.setMainAddr(addrs);
            customersAddrRepository.save(customersAddr);
        }

        customersAddrRepository.addAddrs(username, addrs);
        
        CustomersAddrDTO customersAddrDTO = new CustomersAddrDTO(customersAddrRepository.findByAddrOwner(username));

        return customersAddrDTO;
    }

    public CustomersAddrDTO findCustomersAddrDTO(String username){
        CustomersAddrDTO customersAddrDTO = new CustomersAddrDTO(customersAddrRepository.findByAddrOwner(username));

        return customersAddrDTO;
    }

    public CustomersAddrDTO deleteCustomersAddr(String username, String addrs){
        customersAddrRepository.removeAddrs(username, addrs);

        CustomersAddrDTO customersAddrDTO = new CustomersAddrDTO(customersAddrRepository.findByAddrOwner(username));

        return customersAddrDTO;
    }

    public CustomersAddrDTO setMainAddr(String username, String addrs){
        CustomersAddr customersAddr = customersAddrRepository.findByAddrOwner(username);

        customersAddr.setMainAddr(addrs);
        customersAddrRepository.save(customersAddr);

        CustomersAddrDTO customersAddrDTO = new CustomersAddrDTO(customersAddrRepository.findByAddrOwner(username));

        return customersAddrDTO;
    }
}
