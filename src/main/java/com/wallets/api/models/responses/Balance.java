package com.wallets.api.models.responses;

import java.math.BigDecimal;

public class Balance {
    private BigDecimal walletBalance;
    private String walletCurrency;

    public BigDecimal getWalletBalance() {
        return walletBalance;
    }

    public void setWalletBalance(BigDecimal walletBalance) {
        this.walletBalance = walletBalance;
    }

    public String getWalletCurrency() {
        return walletCurrency;
    }

    public void setWalletCurrency(String walletCurrency) {
        this.walletCurrency = walletCurrency;
    }
}
