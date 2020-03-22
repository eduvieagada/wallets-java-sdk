package com.wallets.api.models.responses.self;

import java.math.BigDecimal;
import java.util.Date;

public class SelfTransactions {
    private BigDecimal amount;
    private String currency;
    private String category;
    private String narration;
    private Date dateTransacted;
    private BigDecimal previousBalance;
    private BigDecimal newBalance;
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

    public Date getDateTransacted() {
        return dateTransacted;
    }

    public void setDateTransacted(Date dateTransacted) {
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
