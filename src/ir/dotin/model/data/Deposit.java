package ir.dotin.model.data;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author : Bahar Zolfaghari
 **/
public abstract class Deposit implements Comparable<Deposit> {
    private String customerNumber;
    private DepositType depositType;
    private BigDecimal depositBalance;
    private int durationInDays;
    private int interestRate;
    private BigDecimal payedInterest;

    /**
     * this method calculates the payed interest on deposit.
     * @return BigDecimal this returns payed interest on deposit.
     * */
    public BigDecimal calculateInterest() {
        return depositBalance
                .multiply(BigDecimal.valueOf(interestRate * durationInDays))
                .divide(BigDecimal.valueOf(36500), 4, RoundingMode.CEILING);
    }

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

    public DepositType getDepositType() {
        return depositType;
    }

    public Deposit setDepositType(DepositType depositType) {
        this.depositType = depositType;
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

    public int getInterestRate() {
        return interestRate;
    }

    public Deposit setInterestRate(int interestRate) {
        this.interestRate = interestRate;
        return this;
    }

    public BigDecimal getPayedInterest() {
        return payedInterest;
    }

    public Deposit setPayedInterest(BigDecimal payedInterest) {
        this.payedInterest = payedInterest;
        return this;
    }
}
