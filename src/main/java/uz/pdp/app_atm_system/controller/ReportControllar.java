package uz.pdp.app_atm_system.controller;

import com.sun.corba.se.pept.encoding.InputObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.app_atm_system.entity.BanknoteBox;
import uz.pdp.app_atm_system.entity.InputMoneyToAtm;
import uz.pdp.app_atm_system.entity.InputOutput;
import uz.pdp.app_atm_system.service.IncomeOutcomeService;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("report")
public class ReportControllar {
    @Autowired
    IncomeOutcomeService incomeOutcomeService;

    @GetMapping
    public HttpEntity<?> getAllIncomeAndOutcome(){
        List<InputOutput> inputOutputList=incomeOutcomeService.getAllIncomeAndOutcome();
        return ResponseEntity.status(200).body(inputOutputList);
    }

    @GetMapping("/allIncomeMoneyToAtm")
    public HttpEntity<?> allIncomeMoneyToAtm(){
        List<InputMoneyToAtm> inputMoneyToAtms=incomeOutcomeService.allIncomeMoneyToAtm();
        return ResponseEntity.status(200).body(inputMoneyToAtms);
    }
    @GetMapping("/dailyIncome")
    public HttpEntity<?> dailyIncome(@RequestBody Date day){
        List<InputOutput> inputOutputList=incomeOutcomeService.dailyIncome(day);
        return ResponseEntity.status(200).body(inputOutputList);
    }

    @GetMapping("/dailyOutcome")
    public HttpEntity<?> dailyOutcome(@RequestBody Date day){
        List<InputOutput> inputOutputList=incomeOutcomeService.dailyOutcome(day);
        return ResponseEntity.status(200).body(inputOutputList);
    }
    @GetMapping("/getBanknoteList/{id}")
    public HttpEntity<?> getBanknoteList(@PathVariable Integer id){
        BanknoteBox banknoteBox =incomeOutcomeService.getBanknoteList(id);
        return ResponseEntity.status(200).body(banknoteBox);
    }
}
