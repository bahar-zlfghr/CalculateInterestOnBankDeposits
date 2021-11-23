package com.dotin.model;

import com.dotin.model.data.*;

/**
 * @author : Bahar Zolfaghari
 **/
public interface DepositFactory {

    /**
     * this method creates deposit object base on deposit type.
     * @param depositType the parameter that deposit object created by it.
     * @return Deposit this returns the created deposit when deposit type is valid otherwise return null.
     * */
    static Deposit createDeposit(DepositType depositType) {
        switch (depositType) {
            case SHORT_TERM:
                return new ShortTermDeposit()
                        .setDepositType(DepositType.SHORT_TERM)
                        .setInterestRate(10);
            case LONG_TERM:
                return new LongTermDeposit()
                        .setDepositType(DepositType.LONG_TERM)
                        .setInterestRate(20);
            case QARZ:
                return new QarzDeposit()
                        .setDepositType(DepositType.QARZ)
                        .setInterestRate(0);
            default:
                return null;
        }
    }
}
