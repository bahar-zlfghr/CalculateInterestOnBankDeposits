package com.dotin.util;

import com.dotin.model.data.Deposit;

import java.math.BigDecimal;

/**
 * @author : Bahar Zolfaghari
 **/
@FunctionalInterface
public interface SetDepositProperties {

    Deposit setProperties(String customerNumber, BigDecimal depositBalance, int durationInDays);
}
