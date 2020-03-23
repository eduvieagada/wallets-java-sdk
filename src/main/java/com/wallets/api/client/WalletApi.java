package com.wallets.api.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wallets.api.exceptions.InvalidRequestException;
import com.wallets.api.exceptions.ServerErrorException;
import com.wallets.api.exceptions.UnauthorizedException;
import com.wallets.api.models.requests.self.BalanceRequest;
import com.wallets.api.models.responses.Balance;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.ObjectMapper;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;

public class WalletApi {
    private static WalletApi walletApi;

    private String publicKey;
    private String secretKey;
    private String baseUrl;

    private WalletApi(String publicKey, String secretKey, Boolean isLive) {
        Unirest.config().setObjectMapper(getObjectMapper());
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

    public Balance getBalance(BalanceRequest balanceRequest) throws InvalidRequestException, ServerErrorException, UnauthorizedException {
        balanceRequest.setSecretKey(secretKey);

        String url = baseUrl + WalletApiUrls.SelfUrls.BALANCE;
        HttpResponse<JsonNode> response =  Unirest.post(url)
                .header("Authorization", "Bearer " + publicKey)
                .header("Content-Type", "application/json")
        .body(balanceRequest)
        .asJson();

        JSONObject body = response.getBody().getObject();

        if (response.getStatus() == 200) {
            JSONObject data = body.getJSONObject("Data");

            BigDecimal walletBalance = data.getBigDecimal("WalletBalance");
            String walletCurrency = data.getString("WalletCurrency");

            var balance = new Balance(walletBalance, walletCurrency);

            return balance;
        }

        if (response.getStatus() == 400) {
            String responseMessage = body.getJSONObject("Response").getString("Message");
            throw new InvalidRequestException(responseMessage);
        }

        if (response.getStatus() == 401) {
            String responseMessage = body.getString("Message");
            throw new UnauthorizedException(responseMessage);
        }

        throw new ServerErrorException("Server Error!");
    }


    private ObjectMapper getObjectMapper() {
        return new ObjectMapper() {
            com.fasterxml.jackson.databind.ObjectMapper mapper
                    = new com.fasterxml.jackson.databind.ObjectMapper();

            public String writeValue(Object value) {
                try {
                    return mapper.writeValueAsString(value);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            public <T> T readValue(String value, Class<T> valueType) {
                try {
                    return mapper.readValue(value, valueType);
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };
    }
}
