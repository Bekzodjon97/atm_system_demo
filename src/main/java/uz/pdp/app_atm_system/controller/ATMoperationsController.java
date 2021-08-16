package uz.pdp.app_atm_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.app_atm_system.entity.Card;
import uz.pdp.app_atm_system.payload.ApiResponse;
import uz.pdp.app_atm_system.payload.LoginDto;
import uz.pdp.app_atm_system.payload.TransferDto;
import uz.pdp.app_atm_system.service.ATMOperationService;

@RestController
@RequestMapping("/api/operation")
public class ATMoperationsController {

    @Autowired
    ATMOperationService atmOperationService;

    @PostMapping("/comeInSystem")
    public HttpEntity<?> comeInSystem(@RequestBody LoginDto loginDto){
        ApiResponse result=atmOperationService.comeInSystem(loginDto);
        return ResponseEntity.status(result.isSuccess()?200:400).body(result);

    }
    @PostMapping("/withdrawMoney")
    public HttpEntity<?> withdrawMoney(@RequestBody TransferDto transferDto){
        ApiResponse result=atmOperationService.withdrawMoney(transferDto);
        return ResponseEntity.status(result.isSuccess()?200:400).body(result);

    }

    @PostMapping("/replenish")
    public HttpEntity<?> replenish(@RequestBody TransferDto transferDto){
        ApiResponse result=atmOperationService.replenish(transferDto);
        return ResponseEntity.status(result.isSuccess()?200:400).body(result);

    }
    @PostMapping("/replenishATM")
    public HttpEntity<?> replenishATM(@RequestBody TransferDto transferDto){
        ApiResponse result=atmOperationService.replenishATM(transferDto);
        return ResponseEntity.status(result.isSuccess()?200:400).body(result);

    }
    @PostMapping("/transfer")
    public HttpEntity<?> transfer(@RequestBody TransferDto transferDto){
        ApiResponse result=atmOperationService.transfer(transferDto);
        return ResponseEntity.status(result.isSuccess()?200:400).body(result);

    }



}
