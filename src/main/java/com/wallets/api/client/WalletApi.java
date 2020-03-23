package com.wallets.api.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wallets.api.exceptions.InvalidRequestException;
import com.wallets.api.exceptions.ServerErrorException;
import com.wallets.api.exceptions.UnauthorizedException;
import com.wallets.api.models.requests.BaseRequest;
import com.wallets.api.models.requests.self.BalanceRequest;
import com.wallets.api.models.requests.self.TransactionsRequest;
import com.wallets.api.models.requests.self.VerifyBvnRequest;
import com.wallets.api.models.responses.ApiResponse;
import com.wallets.api.models.responses.Balance;
import com.wallets.api.models.responses.self.*;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public class WalletApi {
    private static WalletApi walletApi;

    private String publicKey;
    private String secretKey;
    private String baseUrl;

    private WalletApi(String publicKey, String secretKey, Boolean isLive) {
        this.publicKey = publicKey;
        this.secretKey = secretKey;
        this.baseUrl = isLive ? WalletApiUrls.LIVE_BASE_URL : WalletApiUrls.TEST_BASE_URL;
    }

    public static WalletApi getInstance(String publicKey, String secretKey, Boolean isLive) {
        if (walletApi == null) {
            return new WalletApi(publicKey, secretKey, isLive);
        }

        walletApi.publicKey = publicKey;
        walletApi.secretKey = secretKey;
        return walletApi;
    }

    public Balance getBalance(BalanceRequest balanceRequest) throws InvalidRequestException, ServerErrorException, UnauthorizedException, IOException {
        balanceRequest.setSecretKey(secretKey);

        HttpResponse<String> response =  post(WalletApiUrls.SelfUrls.BALANCE, balanceRequest);

        String body = response.getBody();

        if (response.getStatus() == 200) {
            ApiResponse<Balance> result = getObjectMapper().readValue(body, new TypeReference<ApiResponse<Balance>>(){});
            return result.getData();
        }

        ThrowError(response);

        return null;
    }

    public List<SelfTransactions> getTransactionsForSelf(TransactionsRequest request) throws InvalidRequestException, UnauthorizedException, ServerErrorException, ParseException, IOException {
        request.setSecretKey(secretKey);
        HttpResponse<String> res = post(WalletApiUrls.SelfUrls.TRANSACTIONS, request);
        String body = res.getBody();

        if (res.getStatus() == 200) {
            ApiResponse<TransactionModel> result =  getObjectMapper().readValue(body, new TypeReference<ApiResponse<TransactionModel>>(){});
            return result.getData().getTransactions();
        }

        ThrowError(res);
        return null;
    }

    public VerifySelfBvn verifyBvnForSelf(VerifyBvnRequest req) throws ServerErrorException, UnauthorizedException, InvalidRequestException, IOException {
        req.setSecretKey(secretKey);
        HttpResponse<String> res = post(WalletApiUrls.SelfUrls.VERIFY_BVN, req);

        if (res.getStatus() == 200) {
            return getObjectMapper().readValue(res.getBody(), VerifySelfBvn.class);
        }

        ThrowError(res);

        return null;
    }

    public List<Wallet> getWallets(BaseRequest baseRequest) throws IOException, ServerErrorException, UnauthorizedException, InvalidRequestException {
        baseRequest.setSecretKey(secretKey);
        HttpResponse<String> res = post(WalletApiUrls.SelfUrls.GET_WALLETS, baseRequest);

        if (res.getStatus() == 200) {
            ApiResponse<List<Wallet>> result = getObjectMapper().readValue(res.getBody(), new TypeReference<ApiResponse<List<Wallet>>>(){});
            return result.getData();
        }
        ThrowError(res);
        return null;
    }

    public List<WalletTransaction> getWalletToWalletTransactions(TransactionsRequest request) throws IOException, ServerErrorException, UnauthorizedException, InvalidRequestException {
        request.setSecretKey(secretKey);
        HttpResponse<String> response = post(WalletApiUrls.SelfUrls.WALLET_TRANSACTIONS, request);

        if (response.getStatus() == 200) {
            ApiResponse<List<WalletTransaction>> result = getObjectMapper().readValue(response.getBody(), new TypeReference<ApiResponse<List<WalletTransaction>>>(){});
            return result.getData();
        }
        ThrowError(response);
        return null;
    }

    private void ThrowError(HttpResponse<String> res) throws InvalidRequestException, UnauthorizedException, ServerErrorException {
        if (res.getStatus() == 400) {
            throw new InvalidRequestException(res.getBody());
        }

        if (res.getStatus() == 401) {
            throw new UnauthorizedException(res.getBody());
        }

        throw new ServerErrorException("Server Error!");
    }

    private HttpResponse<String> post(String url, Object body) {
        return Unirest.post(baseUrl + url)
                .header("Authorization", "Bearer " + publicKey)
                .header("Content-Type", "application/json")
                .body(body)
                .asString();
    }

    private HttpResponse<String> get(String url) {
        return Unirest.get(url)
                .header("Authorization", "Bearer " + publicKey)
                .header("Content-Type", "application/json")
                .asString();
    }


    private ObjectMapper getObjectMapper() {
        return new ObjectMapper();
    }
}
