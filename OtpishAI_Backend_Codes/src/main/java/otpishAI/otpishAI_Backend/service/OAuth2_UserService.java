package otpishAI.otpishAI_Backend.service;

import lombok.AllArgsConstructor;
import otpishAI.otpishAI_Backend.dto.GoogleResponse;
import otpishAI.otpishAI_Backend.dto.NaverResponse;
import otpishAI.otpishAI_Backend.dto.OAuth2Response;
import otpishAI.otpishAI_Backend.dto.OAuth2_User;
import otpishAI.otpishAI_Backend.dto.OAuth2_CustomersDTO;
import otpishAI.otpishAI_Backend.entity.Customers;
import otpishAI.otpishAI_Backend.entity.CustomersAddr;
import otpishAI.otpishAI_Backend.repository.CustomersAddrRepository;
import otpishAI.otpishAI_Backend.repository.CustomersRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OAuth2_UserService extends DefaultOAuth2UserService {

    private final CustomersRepository customersRepository;
    private final CustomersAddrRepository customersAddrRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response = null;
        if (registrationId.equals("naver")) {

            oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
        }
        else if (registrationId.equals("google")) {

            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
        }
        else {

            return null;
        }
        String username = oAuth2Response.getProvider()+" "+oAuth2Response.getProviderId();
        Customers existData = customersRepository.findByUsername(username);

        if (existData == null) {

            Customers customers = new Customers();
            customers.setUsername(username);
            customers.setEmail(oAuth2Response.getEmail());
            customers.setName(oAuth2Response.getName());
            customers.setRole("ROLE_USER");

            customersRepository.save(customers);

            OAuth2_CustomersDTO OAuth2CustomersDTO = new OAuth2_CustomersDTO();
            OAuth2CustomersDTO.setUsername(username);
            OAuth2CustomersDTO.setName(oAuth2Response.getName());
            OAuth2CustomersDTO.setRole("ROLE_USER");

            CustomersAddr customersAddr = new CustomersAddr();
            customersAddr.setAddrOwner(username);

            customersAddrRepository.save(customersAddr);

            return new OAuth2_User(OAuth2CustomersDTO);
        }
        else {

            existData.setEmail(oAuth2Response.getEmail());
            existData.setName(oAuth2Response.getName());

            customersRepository.save(existData);

            OAuth2_CustomersDTO OAuth2CustomersDTO = new OAuth2_CustomersDTO();
            OAuth2CustomersDTO.setUsername(existData.getUsername());
            OAuth2CustomersDTO.setName(oAuth2Response.getName());
            OAuth2CustomersDTO.setRole(existData.getRole());

            return new OAuth2_User(OAuth2CustomersDTO);
        }


    }
}