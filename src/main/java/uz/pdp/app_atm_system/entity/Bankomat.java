package uz.pdp.app_atm_system.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.app_atm_system.entity.template.CardType;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Bankomat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private CardType intendedCardType;

    private double maxWithdrawalAmount=2000000;

    private double withdrawalCommissionFreOnSameBank;
    private double replenishCommissionFreOnSameBank;
    private double withdrawalCommissionFreOnAnotherBank;
    private double replenishCommissionFreOnAnotherBank;
    private double minMoneyAmount=20000000;


    private double commissionFreOnTransfer;

    @OneToOne
    private Address address;
    private String ownerBank;
    @ManyToOne
    private User user;
}
