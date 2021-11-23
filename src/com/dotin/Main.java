package com.dotin;

import com.dotin.model.data.Deposit;
import com.dotin.model.repository.DepositRepository;

import java.util.List;

/**
 * @author : Bahar Zolfaghari
 **/
public class Main {

    /**
     * this is the main method and start point o program.
     * first, fetch all valid deposits and then sort in descending order and last, save payed interest per deposits.
     * @param args not used.
     * @return void nothing.
     * */
    public static void main(String[] args) {
        List<Deposit> deposits = DepositRepository.getAllDeposits();
        deposits.sort(Deposit::compareTo);
        DepositRepository.writeDepositsPayedInterestInfoInFile();
    }
}
