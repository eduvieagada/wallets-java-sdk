package com.wallets.api.models.requests.transfer;

import com.wallets.api.models.requests.BaseRequest;

public class AccountEnquiryRequest extends BaseRequest {
    private String accountNumber;
    private String bankCode;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }
}
