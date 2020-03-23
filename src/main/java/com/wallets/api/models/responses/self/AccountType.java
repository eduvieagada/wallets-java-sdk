package com.wallets.api.models.responses.self;

import java.util.HashMap;
import java.util.Map;

public enum AccountType {
    TierZero(0),
    TierOne(0),
    TierTwo(0),
    TierThree(0);

    private int value;
    private static Map map = new HashMap<>();

    static {
        for (AccountType accountType : AccountType.values()) {
            map.put(accountType.value, accountType);
        }
    }

    private AccountType(int value) {
        this.value = value;
    }

    public static AccountType valueOf(int accountType) {
        return (AccountType) map.get(accountType);
    }

    public int getValue() {
        return value;
    }
}
