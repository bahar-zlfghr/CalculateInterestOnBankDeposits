package ir.dotin.util;

import ir.dotin.model.data.Deposit;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;

/**
 * @author : Bahar Zolfaghari
 **/
@FunctionalInterface
public interface SetDepositProperties {

    Deposit setProperties(String customerNumber, BigDecimal depositBalance, int durationInDays);
}
