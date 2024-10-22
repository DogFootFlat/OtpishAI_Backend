package otpishAI.otpishAI_Backend.service;

import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import otpishAI.otpishAI_Backend.dto.CartDTO;
import otpishAI.otpishAI_Backend.dto.OrdersDTO;
import otpishAI.otpishAI_Backend.entity.Cart;
import otpishAI.otpishAI_Backend.entity.Orders;
import otpishAI.otpishAI_Backend.entity.ProductDetail;
import otpishAI.otpishAI_Backend.repository.OrdersRepository;
import otpishAI.otpishAI_Backend.repository.ProductDetailRepository;
import otpishAI.otpishAI_Backend.repository.ProductRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrdersService {

    private final OrdersRepository ordersRepository;
    private  final ProductDetailRepository productDetailRepository;

    public List<OrdersDTO> ordersDTOList(String ordersOwner){
        List<Orders> orders = ordersRepository.findAllByOrderOwner(ordersOwner);
        List<OrdersDTO> ordersDTOS = orders.stream()
                .map(order -> new OrdersDTO(order))
                .collect(Collectors.toList());
        return  ordersDTOS;
    }

    public Boolean orderUpdate(Long orderNum, Long orderState){
        try{
            Orders orders = ordersRepository.findByOrderNum(orderNum);
            if(orders == null){
                return false;
            }
            orders.setOrderState(orderState);
            if(orderState == 3){
                orders.setArrivalDate(LocalDateTime.now());
            }
            ordersRepository.save(orders);
            return true;
        }catch (DataAccessException e){
            e.printStackTrace();
            return false;
        }

    }

    public Boolean ordersSave(String ordersOwner, List<CartDTO> cartDTOS){
        try {
            Orders orders = new Orders();

            String[] productDetailCodes = cartDTOS.stream().map(CartDTO::getDetailCode).toArray(String[]::new);
            Long[] orderQuantities = cartDTOS.stream().map(CartDTO::getQuantity).toArray(Long[]::new);
            Long[] orderPrices = cartDTOS.stream().map(CartDTO::getRPrice).toArray(Long[]::new);
            List<ProductDetail> productDetails = productDetailRepository.findAllByDetailCodeIn(productDetailCodes);
            Long[] productNums = productDetails.stream().map(ProductDetail::getProductNum).toArray(Long[]::new);

            orders.setOrderOwner(ordersOwner);
            orders.setProductDetailCode(productDetailCodes);
            orders.setProductNum(productNums);
            orders.setOrderPrice(orderPrices);
            orders.setOrderQuantity(orderQuantities);
            orders.setOrderDate(LocalDateTime.now());
            orders.setTotalPrice(Arrays.stream(orderPrices).filter(Objects::nonNull) .mapToLong(Long::longValue).sum());
            orders.setTotalQuantity(Arrays.stream(orderQuantities).filter(Objects::nonNull) .mapToLong(Long::longValue).sum());
            orders.setOrderState(1L);
            ordersRepository.save(orders);
            return true;

        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

}
