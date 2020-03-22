package com.wallets.api.models.requests.wallet;

import com.wallets.api.models.requests.BaseRequest;

public class GenerateAccountNoRequest extends BaseRequest {
    private String phoneNumber;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
