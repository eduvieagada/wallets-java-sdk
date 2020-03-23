package com.wallets.api.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Response {
    @JsonProperty("ResponseCode")
    private String responseCode;
    @JsonProperty("Message")
    private String message;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
