package ir.dotin.model.data;

/**
 * @author : Bahar Zolfaghari
 **/
public class ShortTermDeposit extends Deposit {
    private static final DepositType depositType = DepositType.SHORT_TERM;
    private static final int interestRate = 10;

    public static DepositType getDepositType() {
        return depositType;
    }

    public static int getInterestRate() {
        return interestRate;
    }
}
