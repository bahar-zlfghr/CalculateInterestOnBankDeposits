package ir.dotin.model.data;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author : Bahar Zolfaghari
 **/
public class LongTermDeposit extends Deposit {
    private static final DepositType depositType = DepositType.LONG_TERM;
    private static final int interestRate = 20;

    public static DepositType getDepositType() {
        return depositType;
    }

    public static int getInterestRate() {
        return interestRate;
    }

    @Override
    public BigDecimal calculateInterest() {
        BigDecimal depositBalance = super.getDepositBalance();
        int durationInDays = super.getDurationInDays();
        return depositBalance.multiply(BigDecimal.valueOf(interestRate * durationInDays)).divide(BigDecimal.valueOf(36500), 4, RoundingMode.CEILING);
    }
}
