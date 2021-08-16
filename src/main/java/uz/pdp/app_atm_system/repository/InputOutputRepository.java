package uz.pdp.app_atm_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.pdp.app_atm_system.entity.InputMoneyToAtm;
import uz.pdp.app_atm_system.entity.InputOutput;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface InputOutputRepository extends JpaRepository<InputOutput, UUID> {

    @Query(value ="select * from input_output where from_card=null and date=:date",nativeQuery = true)
    List<InputOutput> findAllIncomeByDate(Date date);

    @Query(value = "select * from input_output where to_card=null and date=:date",nativeQuery = true)
    List<InputOutput> findAllOutcomeByDate(Date date);

}
