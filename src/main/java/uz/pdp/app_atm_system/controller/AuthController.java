package uz.pdp.app_atm_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.app_atm_system.payload.ApiResponse;
import uz.pdp.app_atm_system.payload.LoginDto;
import uz.pdp.app_atm_system.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public HttpEntity<?> loginUser(@RequestBody LoginDto loginDto){
        ApiResponse apiResponse=authService.login(loginDto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:400).body(apiResponse);
    }
}
