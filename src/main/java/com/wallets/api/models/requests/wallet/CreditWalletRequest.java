package com.wallets.api.models.requests.wallet;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wallets.api.models.requests.BaseRequest;

import java.math.BigDecimal;

public class CreditWalletRequest extends BaseRequest {
    @JsonProperty("TransactionReference")
    private String transactionReference;
    @JsonProperty("Amount")
    private BigDecimal amount;
    @JsonProperty("PhoneNumber")
    private String phoneNumber;

    public String getTransactionReference() {
        return transactionReference;
    }

    public void setTransactionReference(String transactionReference) {
        this.transactionReference = transactionReference;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
