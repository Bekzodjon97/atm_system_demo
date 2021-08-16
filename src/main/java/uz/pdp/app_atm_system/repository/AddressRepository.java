package uz.pdp.app_atm_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.pdp.app_atm_system.entity.Address;
@RepositoryRestResource(path = "address")
public interface AddressRepository extends JpaRepository<Address, Integer> {
}
