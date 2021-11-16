package ir.dotin.model.data;

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
}
