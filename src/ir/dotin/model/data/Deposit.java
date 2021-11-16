package ir.dotin.model.data;

import java.math.BigDecimal;

/**
 * @author : Bahar Zolfaghari
 **/
public abstract class Deposit implements Comparable<Deposit> {
    private String customerNumber;
    private BigDecimal depositBalance;
    private int durationInDays;
    private BigDecimal payedInterest;

    public abstract BigDecimal calculateInterest();

    @Override
    public int compareTo(Deposit deposit) {
        return Integer.compare(0, this.payedInterest.compareTo(deposit.payedInterest));
    }

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

    public BigDecimal getPayedInterest() {
        return payedInterest;
    }

    public Deposit setPayedInterest(BigDecimal payedInterest) {
        this.payedInterest = payedInterest;
        return this;
    }

    @Override
    public String toString() {
        return "Deposit{" +
                "customerNumber='" + customerNumber + '\'' +
                ", depositBalance=" + depositBalance +
                ", durationInDays=" + durationInDays +
                ", payedInterest=" + payedInterest +
                '}';
    }
}
