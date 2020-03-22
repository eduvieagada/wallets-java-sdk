package com.wallets.api.client;

import com.wallets.api.models.requests.self.BalanceRequest;
import com.wallets.api.models.responses.Balance;
import kong.unirest.Unirest;

public class WalletApi {
    private static WalletApi walletApi;
    private String baseUrl = WalletApiUrls.BASE_URL;
    private String publicKey;
    private String secretKey;

    private WalletApi() {

    }

    public static WalletApi getInstance() {
        if (walletApi == null) {
            walletApi = new WalletApi();
        }
        return walletApi;
    }

    public static WalletApi setPublicKey(String publicKey) {
        var walletApi = getInstance();
        walletApi.publicKey = publicKey;
        return walletApi;
    }

    public static WalletApi setSecretKey(String secretKey) {
        var walletApi = getInstance();
        walletApi.secretKey = secretKey;
        return walletApi;
    }

    public Balance getBalance(BalanceRequest balanceRequest) {
        Unirest.post()
    }
}
