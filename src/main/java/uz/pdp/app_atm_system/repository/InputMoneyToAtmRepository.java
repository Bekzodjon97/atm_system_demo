package uz.pdp.app_atm_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.pdp.app_atm_system.entity.Card;
import uz.pdp.app_atm_system.entity.InputMoneyToAtm;

import java.util.Optional;
import java.util.UUID;

public interface InputMoneyToAtmRepository extends JpaRepository<InputMoneyToAtm, UUID> {

}
