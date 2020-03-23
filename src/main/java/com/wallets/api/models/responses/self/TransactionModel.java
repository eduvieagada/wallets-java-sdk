package com.wallets.api.models.responses.self;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class TransactionModel {
    @JsonProperty("Transactions")
    private List<SelfTransactions> transactions;

    public List<SelfTransactions> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<SelfTransactions> transactions) {
        this.transactions = transactions;
    }
}
