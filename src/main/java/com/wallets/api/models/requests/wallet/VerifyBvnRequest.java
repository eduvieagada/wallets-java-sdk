package com.wallets.api.models.requests.wallet;

import com.wallets.api.models.requests.BaseRequest;

import java.util.Date;

public class VerifyBvnRequest extends BaseRequest {
    private Date dateOfBirth;
    private String bvn;
    private String phoneNumber;

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getBvn() {
        return bvn;
    }

    public void setBvn(String bvn) {
        this.bvn = bvn;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
