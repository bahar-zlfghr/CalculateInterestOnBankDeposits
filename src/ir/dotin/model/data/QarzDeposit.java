package ir.dotin.model.data;

/**
 * @author : Bahar Zolfaghari
 **/
public class QarzDeposit extends Deposit {
    private static final DepositType depositType = DepositType.QARZ;
    private static final int interestRate = 0;

    public static DepositType getDepositType() {
        return depositType;
    }

    public static int getInterestRate() {
        return interestRate;
    }
}
