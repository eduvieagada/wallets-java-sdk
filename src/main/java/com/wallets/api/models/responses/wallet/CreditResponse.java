package com.wallets.api.models.responses.wallet;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class CreditResponse {
    private BigDecimal amountCredited;
    private BigDecimal recipientWalletBalance;
    private BigDecimal senderWalletBalance;

    public BigDecimal getAmountCredited() {
        return amountCredited;
    }

    public void setAmountCredited(BigDecimal amountCredited) {
        this.amountCredited = amountCredited;
    }

    public BigDecimal getRecipientWalletBalance() {
        return recipientWalletBalance;
    }

    public void setRecipientWalletBalance(BigDecimal recipientWalletBalance) {
        this.recipientWalletBalance = recipientWalletBalance;
    }

    public BigDecimal getSenderWalletBalance() {
        return senderWalletBalance;
    }

    public void setSenderWalletBalance(BigDecimal senderWalletBalance) {
        this.senderWalletBalance = senderWalletBalance;
    }
}
