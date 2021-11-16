package ir.dotin.model.data;

import java.math.BigDecimal;

/**
 * @author : Bahar Zolfaghari
 **/
public abstract class Deposit {
    private String customerNumber;
    private BigDecimal depositBalance;
    private int durationInDays;

    public String getCustomerNumber() {
        return customerNumber;
    }

    public Deposit setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
        return this;
    }

    public BigDecimal getDepositBalance() {
        return depositBalance;
    }

    public Deposit setDepositBalance(BigDecimal depositBalance) {
        this.depositBalance = depositBalance;
        return this;
    }

    public int getDurationInDays() {
        return durationInDays;
    }

    public Deposit setDurationInDays(int durationInDays) {
        this.durationInDays = durationInDays;
        return this;
    }
}
