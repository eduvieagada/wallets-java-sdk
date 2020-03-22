package com.wallets.api.models.requests.airtime;

import com.wallets.api.models.requests.BaseRequest;

import java.math.BigDecimal;

public class AirtimePurchaseRequest extends BaseRequest {
    private String code;
    private BigDecimal amount;
    private String phoneNumber;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
