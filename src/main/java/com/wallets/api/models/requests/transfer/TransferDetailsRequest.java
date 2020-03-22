package com.wallets.api.models.requests.transfer;

import com.wallets.api.models.requests.BaseRequest;

public class TransferDetailsRequest extends BaseRequest {
    private String transactionRef;

    public String getTransactionRef() {
        return transactionRef;
    }

    public void setTransactionRef(String transactionRef) {
        this.transactionRef = transactionRef;
    }
}
