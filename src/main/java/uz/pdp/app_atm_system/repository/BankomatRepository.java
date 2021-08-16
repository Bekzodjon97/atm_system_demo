package uz.pdp.app_atm_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.pdp.app_atm_system.entity.Address;
import uz.pdp.app_atm_system.entity.Bankomat;
import uz.pdp.app_atm_system.entity.User;

@RepositoryRestResource(path = "bankomat")
public interface BankomatRepository extends JpaRepository<Bankomat, Integer> {
}
