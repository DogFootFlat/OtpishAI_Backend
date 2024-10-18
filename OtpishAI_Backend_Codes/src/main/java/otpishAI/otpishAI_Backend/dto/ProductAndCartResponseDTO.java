package otpishAI.otpishAI_Backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import otpishAI.otpishAI_Backend.entity.Product;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductAndCartResponseDTO {
    private List<CartDTO> cartDTOS;

    private int cartCNT;

    private Page<Product> products;

}