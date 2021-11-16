package ir.dotin.model;

import ir.dotin.model.data.*;

/**
 * @author : Bahar Zolfaghari
 **/
public interface DepositFactory {

    static Deposit createDeposit(DepositType depositType) {
        switch (depositType) {
            case SHORT_TERM:
                return new ShortTermDeposit();
            case LONG_TERM:
                return new LongTermDeposit();
            case QARZ:
                return new QarzDeposit();
            default:
                return null;
        }
    }
}
