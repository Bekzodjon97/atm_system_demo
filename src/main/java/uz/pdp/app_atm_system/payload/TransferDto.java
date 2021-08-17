package uz.pdp.app_atm_system.payload;

import lombok.Data;
import uz.pdp.app_atm_system.entity.Card;

import javax.persistence.ManyToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class TransferDto {
    @NotNull
    private Integer amount;
    @NotNull
    private boolean sumOrDollar;//true sum ,, false dollar
    @NotNull
    private Integer bankomatId;


    private Integer toCardId;


    private Integer mingSum;
    private Integer beshMingSum;
    private Integer onMingSum;
    private Integer ellikMingSum;
    private Integer yuzMingSum;


    private Integer oneDoller;
    private Integer fiveDoller;
    private Integer tenDoller;
    private Integer fiftyDoller;
    private Integer oneHundredDoller;

    public TransferDto(Integer amount, boolean sumOrDollar, Integer bankomatId) {
        this.amount = amount;
        this.sumOrDollar = sumOrDollar;
        this.bankomatId = bankomatId;
    }

    public TransferDto(Integer amount, Integer bankomatId, Integer mingSum, Integer beshMingSum, Integer onMingSum, Integer ellikMingSum, Integer yuzMingSum, Integer oneDoller, Integer fiveDoller, Integer tenDoller, Integer fiftyDoller, Integer oneHundredDoller) {
        this.amount = amount;
        this.bankomatId = bankomatId;
        this.mingSum = mingSum;
        this.beshMingSum = beshMingSum;
        this.onMingSum = onMingSum;
        this.ellikMingSum = ellikMingSum;
        this.yuzMingSum = yuzMingSum;
        this.oneDoller = oneDoller;
        this.fiveDoller = fiveDoller;
        this.tenDoller = tenDoller;
        this.fiftyDoller = fiftyDoller;
        this.oneHundredDoller = oneHundredDoller;
    }

    public TransferDto(Integer amount, boolean sumOrDollar, Integer bankomatId, Integer mingSum, Integer beshMingSum, Integer onMingSum, Integer ellikMingSum, Integer yuzMingSum, Integer oneDoller, Integer fiveDoller, Integer tenDoller, Integer fiftyDoller, Integer oneHundredDoller) {
        this.amount = amount;
        this.sumOrDollar = sumOrDollar;
        this.bankomatId = bankomatId;
        this.mingSum = mingSum;
        this.beshMingSum = beshMingSum;
        this.onMingSum = onMingSum;
        this.ellikMingSum = ellikMingSum;
        this.yuzMingSum = yuzMingSum;
        this.oneDoller = oneDoller;
        this.fiveDoller = fiveDoller;
        this.tenDoller = tenDoller;
        this.fiftyDoller = fiftyDoller;
        this.oneHundredDoller = oneHundredDoller;
    }
}