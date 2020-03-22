package com.wallets.api.models.responses;

public class ApiResponse<T> {
    private String responseCode;
    private String message;
    private T data;

    public ApiResponse(String message, String responseCode) {
        this.message = message;
        this.responseCode = responseCode;
    }

    public ApiResponse(String responseCode) {
        this.responseCode = responseCode;
    }

    public ApiResponse() {}

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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
