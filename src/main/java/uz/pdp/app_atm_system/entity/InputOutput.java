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
public class InputOutput {
    @Id
    @GeneratedValue
    private UUID id;

    @CreatedDate
    private Date date;

    @ManyToOne
    private Card fromCard;//agar fromcard null bo'lsa kirim

    @ManyToOne
    private Card toCard;//agar tocard null bo'lsa chiqim

    @ManyToOne
    private Bankomat bankomat;

    private double amount;
}
