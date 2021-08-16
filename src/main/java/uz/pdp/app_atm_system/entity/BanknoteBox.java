package uz.pdp.app_atm_system.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BanknoteBox {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer mingSum;
    private Integer beshMingSum;
    private Integer onMingSum;
    private Integer ellikMingSum;
    private Integer yuzMingSum;

//    private double totalSum=mingSum*1000+beshMingSum*5000+onMingSum*10000+ellikMingSum*50000+yuzMingSum*100000;
    private double totalSum;
    private Integer oneDoller;
    private Integer fiveDoller;
    private Integer tenDoller;
    private Integer fiftyDoller;
    private Integer oneHundredDoller;

//    private double totalDollar=oneDoller+fiftyDoller*5+tenDoller*10+fiftyDoller*50+oneHundredDoller*100;
    private double totalDollar;
    @OneToOne(optional = false)
    private Bankomat bankomat;



}
