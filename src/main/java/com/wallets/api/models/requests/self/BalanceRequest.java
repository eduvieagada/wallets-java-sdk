package com.wallets.api.models.requests.self;

import com.wallets.api.models.requests.BaseRequest;

public class BalanceRequest extends BaseRequest {
    private String currency;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
