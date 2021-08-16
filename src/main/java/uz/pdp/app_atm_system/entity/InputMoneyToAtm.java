package uz.pdp.app_atm_system.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class InputMoneyToAtm {
    @Id
    @GeneratedValue
    private UUID id;

    @CreatedDate
    private Date date;

    @ManyToOne
    private Bankomat bankomat;

    @ManyToOne
    private User user;

    private double amount;
}
