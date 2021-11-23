package com.dotin.util;

import com.dotin.exception.DepositTypeMismatchException;
import com.dotin.exception.InvalidDepositBalanceException;
import com.dotin.exception.InvalidDurationInDaysException;
import com.dotin.model.data.DepositType;
import com.dotin.model.repository.DepositRepository;

import java.math.BigDecimal;

/**
 * @author : Bahar Zolfaghari
 **/
public interface DepositUtil {

    /**
     * this method check deposit type is valid or not.
     * @param depositType  deposit type will be check that equal to SHORT_TERM, LONG_TERM or QARZ.
     * @param depositIndex deposit index used for error message print if deposit type is not valid.
     * @return void nothing.
     */
    static void checkValidateDepositType(DepositType depositType, int depositIndex) {
        if (depositType == null) {
            String message = "Error[Deposit" + (depositIndex + 1) + "]: The deposit type is not valid!";
            DepositRepository.getExceptions().add(new DepositTypeMismatchException(message));
        }
    }

    /**
     * this method check deposit balance is valid or not.
     * @param depositBalance deposit balance will be check that equal or greater than zero.
     * @param depositIndex   deposit index used for print error message if deposit balance is not valid.
     * @return void nothing.
     */
    static void checkValidateDepositBalance(BigDecimal depositBalance, int depositIndex) {
        if (depositBalance.compareTo(BigDecimal.ZERO) < 0) {
            String message = "Error[Deposit" + (depositIndex + 1) + "]: The deposit balance is less than zero!";
            DepositRepository.getExceptions().add(new InvalidDepositBalanceException(message));
        }
    }

    /**
     * this method check duration in days is valid or not.
     * @param durationInDays duration in days will be check that greater than zero.
     * @param depositIndex   deposit index used for print error message if deposit balance is not valid.
     * @return void nothing.
     */
    static void checkValidateDurationInDays(int durationInDays, int depositIndex) {
        if (durationInDays <= 0) {
            String message = "Error[Deposit" + (depositIndex + 1) + "]: The duration in days is equal or less than zero!";
            DepositRepository.getExceptions().add(new InvalidDurationInDaysException(message));
        }
    }
}
