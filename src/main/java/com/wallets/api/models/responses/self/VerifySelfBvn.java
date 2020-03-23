package com.wallets.api.models.responses.self;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class VerifySelfBvn {
    @JsonProperty("Message")
    private String message;
    @JsonProperty("FirstName")
    private String firstName;
    @JsonProperty("LastName")
    private String lastName;
    @JsonProperty("Email")
    private String email;
    @JsonProperty("WalletBalance")
    private BigDecimal walletBalance;
    @JsonProperty("BVN")
    private String bvn;
    @JsonProperty("HasBVN")
    private Boolean hasBvn;
    @JsonProperty("PassCodeSet")
    private Boolean passCodeSet;
    @JsonProperty("DisplayPicture")
    private String displayPicture;
    @JsonProperty("Username")
    private String username;
    @JsonProperty("UserProgress")
    private UserProgress userProgress;
    @JsonProperty("AccountType")
    private AccountType AccountType;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getWalletBalance() {
        return walletBalance;
    }

    public void setWalletBalance(BigDecimal walletBalance) {
        this.walletBalance = walletBalance;
    }

    public String getBvn() {
        return bvn;
    }

    public void setBvn(String bvn) {
        this.bvn = bvn;
    }

    public Boolean getHasBvn() {
        return hasBvn;
    }

    public void setHasBvn(Boolean hasBvn) {
        this.hasBvn = hasBvn;
    }

    public Boolean getPassCodeSet() {
        return passCodeSet;
    }

    public void setPassCodeSet(Boolean passCodeSet) {
        this.passCodeSet = passCodeSet;
    }

    public String getDisplayPicture() {
        return displayPicture;
    }

    public void setDisplayPicture(String displayPicture) {
        this.displayPicture = displayPicture;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserProgress getUserProgress() {
        return userProgress;
    }

    public void setUserProgress(UserProgress userProgress) {
        this.userProgress = userProgress;
    }

    public com.wallets.api.models.responses.self.AccountType getAccountType() {
        return AccountType;
    }

    public void setAccountType(com.wallets.api.models.responses.self.AccountType accountType) {
        AccountType = accountType;
    }
}
