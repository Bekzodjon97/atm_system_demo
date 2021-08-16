package uz.pdp.app_atm_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.pdp.app_atm_system.entity.Address;
import uz.pdp.app_atm_system.entity.BanknoteBox;

import java.util.Optional;

@RepositoryRestResource(path = "banknoteBox")
public interface BanknoteBoxRepository extends JpaRepository<BanknoteBox, Integer> {
    Optional<BanknoteBox> findByBankomatId(Integer bankomat_id);
}
