package otpishAI.otpishAI_Backend.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class OAuth2_User implements OAuth2User {

    private final OAuth2_CustomersDTO OAuth2CustomersDTO;

    public OAuth2_User(OAuth2_CustomersDTO OAuth2CustomersDTO) {

        this.OAuth2CustomersDTO = OAuth2CustomersDTO;
    }

    @Override
    public Map<String, Object> getAttributes() {

        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {

            @Override
            public String getAuthority() {

                return OAuth2CustomersDTO.getRole();
            }
        });

        return collection;
    }

    @Override
    public String getName() {

        return OAuth2CustomersDTO.getName();
    }

    public String getUsername() {

        return OAuth2CustomersDTO.getUsername();
    }
}