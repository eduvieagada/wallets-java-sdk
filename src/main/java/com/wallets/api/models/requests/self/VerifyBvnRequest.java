package com.wallets.api.models.requests.self;

import com.wallets.api.models.requests.BaseRequest;

import java.util.Date;

public class VerifyBvnRequest extends BaseRequest {
    private String bvn;
    private Date dateOfBirth;

    public String getBvn() {
        return bvn;
    }

    public void setBvn(String bvn) {
        this.bvn = bvn;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
