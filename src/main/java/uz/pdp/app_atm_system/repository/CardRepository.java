package uz.pdp.app_atm_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.pdp.app_atm_system.entity.Bankomat;
import uz.pdp.app_atm_system.entity.Card;

import java.util.Optional;

@RepositoryRestResource(path = "card")
public interface CardRepository extends JpaRepository<Card, Integer> {
    Optional<Card> findByCardNumber(String cardNumber);
}
