package com.wallets.api.models.requests.wallet;

import com.wallets.api.models.requests.BaseRequest;

public class WalletBalanceRequest extends BaseRequest {
    private String phoneNumber;
    private String currency;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
