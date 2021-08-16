package uz.pdp.app_atm_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.pdp.app_atm_system.entity.*;
import uz.pdp.app_atm_system.entity.template.RoleName;
import uz.pdp.app_atm_system.repository.BanknoteBoxRepository;
import uz.pdp.app_atm_system.repository.InputMoneyToAtmRepository;
import uz.pdp.app_atm_system.repository.InputOutputRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class IncomeOutcomeService {
    @Autowired
    InputOutputRepository inputOutputRepository;
    @Autowired
    InputMoneyToAtmRepository inputMoneyToAtmRepository;
    @Autowired
    BanknoteBoxRepository banknoteBoxRepository;

    public List<InputOutput> getAllIncomeAndOutcome() {
        User userInSystem =(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Set<Role> roles = userInSystem.getRole();
        for (Role role : roles) {
            if (role.getRoleName().equals(RoleName.DIRECTOR)){
                return  inputOutputRepository.findAll();
            }
        }
        return null;
    }

    public List<InputMoneyToAtm> allIncomeMoneyToAtm() {
        User userInSystem =(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Set<Role> roles = userInSystem.getRole();
        for (Role role : roles) {
            if (role.getRoleName().equals(RoleName.DIRECTOR)){
                return  inputMoneyToAtmRepository.findAll();
            }
        }
        return null;
    }

    public List<InputOutput> dailyIncome(Date day) {
        User userInSystem =(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Set<Role> roles = userInSystem.getRole();
        for (Role role : roles) {
            if (role.getRoleName().equals(RoleName.DIRECTOR)){
                return  inputOutputRepository.findAllIncomeByDate(day);
            }
        }
        return null;
    }

    public List<InputOutput> dailyOutcome(Date day) {
        User userInSystem =(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Set<Role> roles = userInSystem.getRole();
        for (Role role : roles) {
            if (role.getRoleName().equals(RoleName.DIRECTOR)) {
                return inputOutputRepository.findAllOutcomeByDate(day);
            }
        }
        return null;
    }

    public BanknoteBox getBanknoteList(Integer id) {
        User userInSystem =(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Set<Role> roles = userInSystem.getRole();
        for (Role role : roles) {
            if (role.getRoleName().equals(RoleName.DIRECTOR)) {
                Optional<BanknoteBox> optionalBanknoteBox = banknoteBoxRepository.findByBankomatId(id);
                return optionalBanknoteBox.orElseGet(BanknoteBox::new);
            }
        }
        return null;
    }
}
