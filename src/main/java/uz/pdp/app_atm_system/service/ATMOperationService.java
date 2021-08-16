package uz.pdp.app_atm_system.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.app_atm_system.entity.*;
import uz.pdp.app_atm_system.payload.ApiResponse;
import uz.pdp.app_atm_system.payload.LoginDto;
import uz.pdp.app_atm_system.payload.TransferDto;
import uz.pdp.app_atm_system.repository.*;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;
import java.util.Properties;

@Service
public class ATMOperationService {
    @Autowired
    CardRepository cardRepository;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    BankomatRepository  bankomatRepository;
    @Autowired
    BanknoteBoxRepository banknoteBoxRepository;
    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    InputMoneyToAtmRepository inputMoneyToAtmRepository;
    @Autowired
    InputOutputRepository inputOutputRepository;



    public ApiResponse comeInSystem(LoginDto loginDto) {
        Optional<Card> optionalCard = cardRepository.findByCardNumber(loginDto.getUsername());
        Card card = optionalCard.get();
        if (card.getPassword().equals(loginDto.getPassword())&&card.isActive()){
            String stt=card.getCardNumber()+":"+card.getPassword();
            byte[] bytes = stt.getBytes(StandardCharsets.UTF_8);
            byte[] encode = Base64.getEncoder().encode(bytes);
            String str=new String(encode);
            return new ApiResponse("Basic code",true,str);
        }
        return new ApiResponse("Pin Code xato", false);
    }

    @Transactional
    public ApiResponse withdrawMoney(TransferDto transferDto) {
        Card cardInSystem = (Card) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Bankomat> optionalBankomat = bankomatRepository.findById(transferDto.getBankomatId());
        Bankomat bankomat = optionalBankomat.get();
        double commissionFree;
        if (bankomat.getIntendedCardType().equals(cardInSystem.getCardType())) {
            if (cardInSystem.getBankType().equals(bankomat.getOwnerBank())) {
                commissionFree = bankomat.getWithdrawalCommissionFreOnSameBank();
            } else {
                commissionFree = bankomat.getWithdrawalCommissionFreOnAnotherBank();
            }
            int amount = transferDto.getAmount();
            if (cardInSystem.getBalance() >= amount + amount*(commissionFree/100)) {
                Optional<BanknoteBox> optionalBanknoteBox = banknoteBoxRepository.findByBankomatId(bankomat.getId());
                BanknoteBox banknoteBox = optionalBanknoteBox.get();
                if ((transferDto.isSumOrDollar()?banknoteBox.getTotalSum():banknoteBox.getTotalDollar())>=amount){
                    if (transferDto.isSumOrDollar()){
                        int temp1=amount/100000;
                        if (temp1<=banknoteBox.getYuzMingSum()) {
                            banknoteBox.setYuzMingSum(banknoteBox.getYuzMingSum()-temp1);
                            amount=amount-temp1*100000;
                        }else {
                            amount=amount%100000+(temp1-banknoteBox.getYuzMingSum())*100000;
                            banknoteBox.setYuzMingSum(0);
                        }
                        int temp2=amount/50000;
                        if (temp2<=banknoteBox.getEllikMingSum()) {
                            banknoteBox.setEllikMingSum(banknoteBox.getEllikMingSum()-temp2);
                            amount=amount-temp2*50000;
                        }else {
                            amount=amount%50000+(temp2-banknoteBox.getEllikMingSum())*50000;
                            banknoteBox.setEllikMingSum(0);
                        }
                        int temp3=amount/10000;
                        if (temp3<=banknoteBox.getOnMingSum()) {
                            banknoteBox.setOnMingSum(banknoteBox.getOnMingSum()-temp3);
                            amount=amount-temp3*10000;
                        }else {
                            amount=amount%10000+(temp3-banknoteBox.getOnMingSum())*10000;
                            banknoteBox.setOnMingSum(0);
                        }
                        int temp4=amount/5000;
                        if (temp4<=banknoteBox.getBeshMingSum()) {
                            banknoteBox.setBeshMingSum(banknoteBox.getBeshMingSum()-temp4);
                            amount=amount-temp4*5000;
                        }else {
                            amount=amount%5000+(temp4-banknoteBox.getBeshMingSum())*5000;
                            banknoteBox.setBeshMingSum(0);
                        }
                        int temp5=amount/1000;
                        if (temp5<=banknoteBox.getMingSum()) {
                            banknoteBox.setMingSum(banknoteBox.getMingSum()-temp5);
                            BanknoteBox savedBanknoteBox = banknoteBoxRepository.save(banknoteBox);
                            cardInSystem.setBalance(cardInSystem.getBalance()-(amount + amount*(commissionFree/100)));
                            cardRepository.save(cardInSystem);
                            InputOutput inputOutput=new InputOutput();
                            inputOutput.setFromCard(cardInSystem);
                            inputOutput.setDate(new Date());
                            inputOutput.setAmount(transferDto.getAmount());
                            inputOutput.setBankomat(bankomat);
                            inputOutputRepository.save(inputOutput);
                            if (savedBanknoteBox.getTotalSum()<20000000) {
                                sendEmail(bankomat.getUser().getEmail(),bankomat.getId());
                            }
                            return new ApiResponse("Operatsiyani  muaffaqiyatli amalga oshirildi",true);
                        }else {
                            return new ApiResponse("Operatsiyani amalga oshirib bo'lmaydi",false);
                        }

                    }
                    if (!transferDto.isSumOrDollar()){
                        int temp1=amount/100;
                        if (temp1<=banknoteBox.getOneHundredDoller()) {
                            banknoteBox.setOneHundredDoller(banknoteBox.getOneHundredDoller()-temp1);
                            amount=amount-temp1*100;
                        }else {
                            amount=amount%100+(temp1-banknoteBox.getOneHundredDoller())*100;
                            banknoteBox.setYuzMingSum(0);
                        }
                        int temp2=amount/50;
                        if (temp2<=banknoteBox.getFiftyDoller()) {
                            banknoteBox.setFiftyDoller(banknoteBox.getFiftyDoller()-temp2);
                            amount=amount-temp2*50;
                        }else {
                            amount=amount%50+(temp2-banknoteBox.getFiftyDoller())*50;
                            banknoteBox.setFiftyDoller(0);
                        }
                        int temp3=amount/10;
                        if (temp3<=banknoteBox.getTenDoller()) {
                            banknoteBox.setTenDoller(banknoteBox.getTenDoller()-temp3);
                            amount=amount-temp3*10;
                        }else {
                            amount=amount%10+(temp3-banknoteBox.getTenDoller())*10;
                            banknoteBox.setTenDoller(0);
                        }
                        int temp4=amount/5;
                        if (temp4<=banknoteBox.getFiveDoller()) {
                            banknoteBox.setFiveDoller(banknoteBox.getFiveDoller()-temp4);
                            amount=amount-temp4*5;
                        }else {
                            amount=amount%5+(temp4-banknoteBox.getFiveDoller())*5;
                            banknoteBox.setFiveDoller(0);
                        }
                        if (amount<=banknoteBox.getOneDoller()) {
                            banknoteBox.setOneDoller(banknoteBox.getOneDoller()-amount);
                            BanknoteBox savedBanknoteBox = banknoteBoxRepository.save(banknoteBox);
                            cardInSystem.setBalance(cardInSystem.getBalance()-(amount + amount*(commissionFree/100)));
                            cardRepository.save(cardInSystem);
                            InputOutput inputOutput=new InputOutput();
                            inputOutput.setFromCard(cardInSystem);
                            inputOutput.setDate(new Date());
                            inputOutput.setAmount(transferDto.getAmount());
                            inputOutput.setBankomat(bankomat);
                            inputOutputRepository.save(inputOutput);
                            if (savedBanknoteBox.getTotalSum()<20000000) {
                                sendEmail(bankomat.getUser().getEmail(),bankomat.getId());
                            }
                            return new ApiResponse("Operatsiyani muaffaqiyatli amalga oshirildi",true);
                        }else {
                            return new ApiResponse("Operatsiyani amalga oshirib bo'lmaydi",false);
                        }

                    }
                }
                return new ApiResponse("Bankomatda mablag' yetarli emas", false);
            }
            return new ApiResponse("Hisobingizda mablag' yetarli emas", false);
        }
        return new ApiResponse("Bankomat "+cardInSystem.getCardType()+" turdagi cartaga xizmat ko'rsatmaydi", false);
    }

    public boolean sendEmail(String senderEmail, Integer bankomatId){
        try {
            SimpleMailMessage mailMessage=new SimpleMailMessage();
            mailMessage.setFrom("ATMinfo.com");
            mailMessage.setTo(senderEmail);
            mailMessage.setSubject("Ogohlantiruvchi xabar");
            mailMessage.setText(bankomatId+" ushbu idli bankomatda pul 20000000 dan kam qoldi");
            javaMailSender.send(mailMessage);
            return true;
        }catch (Exception exception){
            return false;
        }

    }



    @Transactional
    public ApiResponse replenish(TransferDto transferDto) {
        Card cardInSystem = (Card) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Bankomat> optionalBankomat = bankomatRepository.findById(transferDto.getBankomatId());
        Bankomat bankomat = optionalBankomat.get();
        double commissionFree;
        if (bankomat.getIntendedCardType().equals(cardInSystem.getCardType())) {
            if (cardInSystem.getBankType().equals(bankomat.getOwnerBank())) {
                commissionFree = bankomat.getReplenishCommissionFreOnSameBank();
            } else {
                commissionFree = bankomat.getReplenishCommissionFreOnAnotherBank();
            }
                Optional<BanknoteBox> optionalBanknoteBox = banknoteBoxRepository.findByBankomatId(bankomat.getId());
                BanknoteBox banknoteBox = optionalBanknoteBox.get();
                int amount = transferDto.getAmount();
                    if (transferDto.isSumOrDollar()){
                        cardInSystem.setBalance(cardInSystem.getBalance()+(amount-(amount*(commissionFree/100))));
                        banknoteBox.setMingSum(banknoteBox.getMingSum()+transferDto.getMingSum());
                        banknoteBox.setBeshMingSum(banknoteBox.getBeshMingSum()+transferDto.getBeshMingSum());
                        banknoteBox.setOnMingSum(banknoteBox.getOnMingSum()+transferDto.getOnMingSum());
                        banknoteBox.setEllikMingSum(banknoteBox.getEllikMingSum()+transferDto.getEllikMingSum());
                        banknoteBox.setYuzMingSum(banknoteBox.getYuzMingSum()+transferDto.getYuzMingSum());
                        BanknoteBox savedBanknoteBox = banknoteBoxRepository.save(banknoteBox);
                        InputOutput inputOutput=new InputOutput();
                        inputOutput.setAmount(transferDto.getAmount());
                        inputOutput.setDate(new Date());
                        inputOutput.setToCard(cardInSystem);
                        inputOutput.setBankomat(bankomat);
                        inputOutputRepository.save(inputOutput);
                        return new ApiResponse("Mablag' hisobingizga o'tkazildi",true);
                    }
                    if (!transferDto.isSumOrDollar()){
                        cardInSystem.setBalance(cardInSystem.getBalance()+(amount-(amount*(commissionFree/100))));
                        banknoteBox.setOneDoller(banknoteBox.getOneDoller()+transferDto.getOneDoller());
                        banknoteBox.setFiveDoller(banknoteBox.getFiveDoller()+transferDto.getFiveDoller());
                        banknoteBox.setTenDoller(banknoteBox.getTenDoller()+transferDto.getTenDoller());
                        banknoteBox.setFiftyDoller(banknoteBox.getFiftyDoller()+transferDto.getFiftyDoller());
                        banknoteBox.setOneHundredDoller(banknoteBox.getOneHundredDoller()+transferDto.getOneHundredDoller());
                        BanknoteBox savedBanknoteBox = banknoteBoxRepository.save(banknoteBox);

                        return new ApiResponse("Mablag' hisobingizga o'tkazildi",true);

                    }

        }
        return new ApiResponse("Bankomat "+cardInSystem.getCardType()+" turdagi cartaga xizmat ko'rsatmaydi", false);
    }

    public ApiResponse replenishATM(TransferDto transferDto) {
        User userInSystem = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Bankomat> optionalBankomat = bankomatRepository.findById(transferDto.getBankomatId());
        Bankomat bankomat = optionalBankomat.get();
        if (bankomat.getUser().equals(userInSystem)){
            Optional<BanknoteBox> optionalBanknoteBox = banknoteBoxRepository.findByBankomatId(bankomat.getId());
            BanknoteBox banknoteBox = optionalBanknoteBox.get();
                banknoteBox.setMingSum(banknoteBox.getMingSum()+transferDto.getMingSum());
                banknoteBox.setBeshMingSum(banknoteBox.getBeshMingSum()+transferDto.getBeshMingSum());
                banknoteBox.setOnMingSum(banknoteBox.getOnMingSum()+transferDto.getOnMingSum());
                banknoteBox.setEllikMingSum(banknoteBox.getEllikMingSum()+transferDto.getEllikMingSum());
                banknoteBox.setYuzMingSum(banknoteBox.getYuzMingSum()+transferDto.getYuzMingSum());
                banknoteBox.setOneDoller(banknoteBox.getOneDoller()+transferDto.getOneDoller());
                banknoteBox.setFiveDoller(banknoteBox.getFiveDoller()+transferDto.getFiveDoller());
                banknoteBox.setTenDoller(banknoteBox.getTenDoller()+transferDto.getTenDoller());
                banknoteBox.setFiftyDoller(banknoteBox.getFiftyDoller()+transferDto.getFiftyDoller());
                banknoteBox.setOneHundredDoller(banknoteBox.getOneHundredDoller()+transferDto.getOneHundredDoller());
                BanknoteBox savedBanknoteBox = banknoteBoxRepository.save(banknoteBox);
            InputMoneyToAtm inputMoneyToAtm=new InputMoneyToAtm();
            inputMoneyToAtm.setAmount(transferDto.getAmount());
            inputMoneyToAtm.setBankomat(bankomat);
            inputMoneyToAtm.setDate(new Date());
            inputMoneyToAtm.setUser(bankomat.getUser());
            inputMoneyToAtmRepository.save(inputMoneyToAtm);

                return new ApiResponse("Bankomatga pul qabul qilindi",true);

        }
        return new ApiResponse("Kechirasiz siz bu amaliyotni amalga oshirolmaysiz",false);
    }

    @Transactional
    public ApiResponse transfer(TransferDto transferDto) {
        Card cardInSystem = (Card) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Bankomat> optionalBankomat = bankomatRepository.findById(transferDto.getBankomatId());
        Bankomat bankomat = optionalBankomat.get();
        if (cardInSystem.getBalance()>=transferDto.getAmount()+transferDto.getAmount()*(bankomat.getCommissionFreOnTransfer()/100)){
           cardInSystem.setBalance(cardInSystem.getBalance()-(transferDto.getAmount()+transferDto.getAmount()*(bankomat.getCommissionFreOnTransfer()/100)));
            Optional<Card> optionalCard = cardRepository.findById(transferDto.getToCard().getId());
            if (optionalCard.isPresent()) {
                Card card = optionalCard.get();
                card.setBalance(card.getBalance()+transferDto.getAmount());
                cardRepository.save(cardInSystem);
                cardRepository.save(card);
                InputOutput inputOutput=new InputOutput();
                inputOutput.setBankomat(bankomat);
                inputOutput.setDate(new Date());
                inputOutput.setAmount(transferDto.getAmount());
                inputOutput.setFromCard(cardInSystem);
                inputOutput.setToCard(card);
                inputOutputRepository.save(inputOutput);
                return new ApiResponse("Mablag' o'tkazildi", true);
            }else {
                return new ApiResponse("Karta topilmadi", false);
            }
        }
        return new ApiResponse("Mablag' yetarli emas", false);
    }
}
