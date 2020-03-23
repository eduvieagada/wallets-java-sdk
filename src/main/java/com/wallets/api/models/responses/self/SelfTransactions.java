package com.wallets.api.models.responses.self;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Date;

public class SelfTransactions {
    @JsonProperty("Amount")
    private BigDecimal amount;
    @JsonProperty("Currency")
    private String currency;
    @JsonProperty("Category")
    private String category;
    @JsonProperty("Narration")
    private String narration;
    @JsonProperty("DateTransacted")
    private String dateTransacted;
    @JsonProperty("PreviousBalance")
    private BigDecimal previousBalance;
    @JsonProperty("NewBalance")
    private BigDecimal newBalance;
    @JsonProperty("Type")
    private String type;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    public String getDateTransacted() {
        return dateTransacted;
    }

    public void setDateTransacted(String dateTransacted) {
        this.dateTransacted = dateTransacted;
    }

    public BigDecimal getPreviousBalance() {
        return previousBalance;
    }

    public void setPreviousBalance(BigDecimal previousBalance) {
        this.previousBalance = previousBalance;
    }

    public BigDecimal getNewBalance() {
        return newBalance;
    }

    public void setNewBalance(BigDecimal newBalance) {
        this.newBalance = newBalance;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
