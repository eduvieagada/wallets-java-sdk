package com.wallets.api.models.responses.wallet;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class DebitResponse {
    @JsonProperty("AmountCredited")
    private BigDecimal amountCredited;
    @JsonProperty("CustomerWalletBalance")
    private BigDecimal customerWalletBalance;
    @JsonProperty("DeveloperWalletBalance")
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
