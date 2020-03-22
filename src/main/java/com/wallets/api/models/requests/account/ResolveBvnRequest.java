package com.wallets.api.models.requests.account;

import com.wallets.api.models.requests.BaseRequest;

public class ResolveBvnRequest extends BaseRequest {
    private String bvn;

    public String getBvn() {
        return bvn;
    }

    public void setBvn(String bvn) {
        this.bvn = bvn;
    }
}
