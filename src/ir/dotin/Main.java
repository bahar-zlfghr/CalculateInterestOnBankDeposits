package ir.dotin;

import ir.dotin.model.data.Deposit;
import ir.dotin.model.repository.DepositRepository;

import java.util.List;

/**
 * @author : Bahar Zolfaghari
 **/
public class Main {

    public static void main(String[] args) {
        List<Deposit> deposits = DepositRepository.getAllDeposits();
        deposits.sort(Deposit::compareTo);
        DepositRepository.writeDepositsPayedInterestInfoInFile();
    }
}
