package com.wallets.api.models.responses.airtime;

import java.util.List;

public class AirtimeModel {
    private String responseCode;
    private List<AirtimeProviders> providers;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public List<AirtimeProviders> getProviders() {
        return providers;
    }

    public void setProviders(List<AirtimeProviders> providers) {
        this.providers = providers;
    }
}
