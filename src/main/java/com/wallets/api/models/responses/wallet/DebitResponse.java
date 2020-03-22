package com.wallets.api.models.responses.wallet;

import java.math.BigDecimal;

public class DebitResponse {
    private BigDecimal amountCredited;
    private BigDecimal customerWalletBalance;
    private BigDecimal businessWalletBalance;

    public BigDecimal getAmountCredited() {
        return amountCredited;
    }

    public void setAmountCredited(BigDecimal amountCredited) {
        this.amountCredited = amountCredited;
    }

    public BigDecimal getCustomerWalletBalance() {
        return customerWalletBalance;
    }

    public void setCustomerWalletBalance(BigDecimal customerWalletBalance) {
        this.customerWalletBalance = customerWalletBalance;
    }

    public BigDecimal getBusinessWalletBalance() {
        return businessWalletBalance;
    }

    public void setBusinessWalletBalance(BigDecimal businessWalletBalance) {
        this.businessWalletBalance = businessWalletBalance;
    }
}
