package com.dotin.model.data;

/**
 * @author : Bahar Zolfaghari
 **/
public enum DepositType {
    SHORT_TERM, LONG_TERM, QARZ;

    public static DepositType getDepositType(String depositType) {
        switch (depositType) {
            case "ShortTerm":
                return SHORT_TERM;
            case "LongTerm":
                return LONG_TERM;
            case "Qarz":
                return QARZ;
            default:
                return null;
        }
    }
}
