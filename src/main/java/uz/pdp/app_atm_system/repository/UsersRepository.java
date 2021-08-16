package uz.pdp.app_atm_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.pdp.app_atm_system.entity.Card;
import uz.pdp.app_atm_system.entity.User;

import javax.validation.constraints.Email;
import java.util.Optional;
import java.util.UUID;
public interface UsersRepository extends JpaRepository<User, UUID> {


    Optional<User> findByEmail(@Email String email);
}
