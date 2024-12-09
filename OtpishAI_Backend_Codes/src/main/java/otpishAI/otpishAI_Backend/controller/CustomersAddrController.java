
package otpishAI.otpishAI_Backend.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import otpishAI.otpishAI_Backend.dto.CustomersAddrDTO;
import otpishAI.otpishAI_Backend.dto.CustomersDTO;
import otpishAI.otpishAI_Backend.dto.WishlistDTO;
import otpishAI.otpishAI_Backend.jwt.JWTUtil;
import otpishAI.otpishAI_Backend.service.CustomersAddrService;
import otpishAI.otpishAI_Backend.service.CustomersService;
import otpishAI.otpishAI_Backend.service.RefreshTCheckService;
import otpishAI.otpishAI_Backend.service.WishlistService;

@RestController
@AllArgsConstructor
public class CustomersAddrController {


    private final JWTUtil jwtUtil;

    private final RefreshTCheckService refreshTCheckService;

    private final CustomersAddrService customersAddrService;

    @PostMapping("/addr/add/{addr}")
    public ResponseEntity<?> addAddr(@PathVariable("addr") String addrs, HttpServletRequest request, HttpServletResponse response) {
        String refresh = refreshTCheckService.RefreshTCheck(request, response);

        if (!refresh.equals("")) {
            CustomersAddrDTO customersAddrDTO = customersAddrService.addCustomersAddr(jwtUtil.getUsername(refresh), addrs);

            return new ResponseEntity<>(customersAddrDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/addr/delete/{addr}")
    public ResponseEntity<?> deleteAddr(@PathVariable("addr") String addrs, HttpServletRequest request, HttpServletResponse response) {
        String refresh = refreshTCheckService.RefreshTCheck(request, response);

        if (!refresh.equals("")) {
            CustomersAddrDTO customersAddrDTO = customersAddrService.deleteCustomersAddr(jwtUtil.getUsername(refresh), addrs);
            return new ResponseEntity<>(customersAddrDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }


    @PostMapping("/addr/setmain/{addr}")
    public ResponseEntity<?> setMainAddr(@PathVariable("addr") String addrs, HttpServletRequest request, HttpServletResponse response) {
        String refresh = refreshTCheckService.RefreshTCheck(request, response);

        if (!refresh.equals("")) {
            CustomersAddrDTO customersAddrDTO = customersAddrService.setMainAddr(jwtUtil.getUsername(refresh), addrs);
            return new ResponseEntity<>(customersAddrDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/addr")
    public ResponseEntity<?> setMainAddr(HttpServletRequest request, HttpServletResponse response) {
        String refresh = refreshTCheckService.RefreshTCheck(request, response);

        if (!refresh.equals("")) {
            CustomersAddrDTO customersAddrDTO = customersAddrService.findCustomersAddrDTO(jwtUtil.getUsername(refresh));
            return new ResponseEntity<>(customersAddrDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}