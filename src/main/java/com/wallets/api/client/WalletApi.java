package com.wallets.api.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wallets.api.exceptions.InvalidRequestException;
import com.wallets.api.exceptions.ServerErrorException;
import com.wallets.api.exceptions.UnauthorizedException;
import com.wallets.api.models.requests.self.BalanceRequest;
import com.wallets.api.models.requests.self.TransactionsRequest;
import com.wallets.api.models.requests.self.VerifyBvnRequest;
import com.wallets.api.models.responses.Balance;
import com.wallets.api.models.responses.self.AccountType;
import com.wallets.api.models.responses.self.SelfTransactions;
import com.wallets.api.models.responses.self.UserProgress;
import com.wallets.api.models.responses.self.VerifySelfBvn;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.ObjectMapper;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

        HttpResponse<JsonNode> response =  post(WalletApiUrls.SelfUrls.BALANCE, balanceRequest);

        JSONObject body = response.getBody().getObject();

        if (response.getStatus() == 200) {
            JSONObject data = body.getJSONObject("Data");

            BigDecimal walletBalance = data.getBigDecimal("WalletBalance");
            String walletCurrency = data.getString("WalletCurrency");

            var balance = new Balance(walletBalance, walletCurrency);

            return balance;
        }

        ThrowError(response, body);

        return null;
    }

    public List<SelfTransactions> getTransactionsForSelf(TransactionsRequest request) throws InvalidRequestException, UnauthorizedException, ServerErrorException, ParseException {
        request.setSecretKey(secretKey);

        HttpResponse<JsonNode> res = post(WalletApiUrls.SelfUrls.TRANSACTIONS, request);

        JSONObject body = res.getBody().getObject();

        if (res.getStatus() == 200) {
            JSONObject data = body.getJSONObject("Data");
            JSONArray transactions = data.getJSONArray("Transactions");

            Stream<SelfTransactions> resultStream = transactions.toList().stream().map(t -> {
                var tData =  (JSONObject)t;
                SelfTransactions selfTransactions = new SelfTransactions();
                selfTransactions.setAmount(tData.getBigDecimal("Amount"));
                selfTransactions.setCategory(tData.getString("Category"));
                selfTransactions.setNarration(tData.getString("Narration"));
                selfTransactions.setDateTransacted(tData.getString("DateTransacted"));
                selfTransactions.setPreviousBalance(tData.getBigDecimal("PreviousBalance"));
                selfTransactions.setNewBalance(tData.getBigDecimal("NewBalance"));
                selfTransactions.setType(tData.getString("Type"));

                return selfTransactions;
            });

            List<SelfTransactions> result = resultStream.collect(Collectors.toList());

            return result;
        }

        ThrowError(res, body);

        return null;
    }

    public VerifySelfBvn verifyBvnForSelf(VerifyBvnRequest req) throws ServerErrorException, UnauthorizedException, InvalidRequestException {
        req.setSecretKey(secretKey);
        HttpResponse<JsonNode> res = post(WalletApiUrls.SelfUrls.VERIFY_BVN, req);
        JSONObject body = res.getBody().getObject();

        if (res.getStatus() == 200) {
            var verifySelfBvn = new VerifySelfBvn();
            verifySelfBvn.setAccountType(AccountType.valueOf(body.getInt("AccountType")));
            verifySelfBvn.setBvn(body.getString("BVN"));

            try {
                verifySelfBvn.setDisplayPicture(body.getString("DisplayPicture"));
            } catch (Exception ex){
            }

            try {
                verifySelfBvn.setUsername(body.getString("Username"));
            } catch (Exception e){
            }

            verifySelfBvn.setEmail(body.getString("Email"));
            verifySelfBvn.setFirstName(body.getString("FirstName"));
            verifySelfBvn.setLastName(body.getString("LastName"));
            verifySelfBvn.setHasBvn(body.getBoolean("HasBVN"));
            verifySelfBvn.setUserProgress(UserProgress.valueOf(body.getInt("UserProgress")));

            verifySelfBvn.setWalletBalance(body.getBigDecimal("WalletBalance"));

            return verifySelfBvn;
        }

        ThrowError(res, body);

        return null;
    }

    private void ThrowError(HttpResponse<JsonNode> res, JSONObject body) throws InvalidRequestException, UnauthorizedException, ServerErrorException {
        if (res.getStatus() == 400) {
            String responseMessage = body.getJSONObject("Response").getString("Message");
            throw new InvalidRequestException(responseMessage);
        }

        if (res.getStatus() == 401) {
            String responseMessage = body.getString("Message");
            throw new UnauthorizedException(responseMessage);
        }

        throw new ServerErrorException("Server Error!");
    }

    private HttpResponse<JsonNode> post(String url, Object body) {
        return Unirest.post(baseUrl + url)
                .header("Authorization", "Bearer " + publicKey)
                .header("Content-Type", "application/json")
                .body(body)
                .asJson();
    }

    private HttpResponse<JsonNode> get(String url) {
        return Unirest.get(url)
                .header("Authorization", "Bearer " + publicKey)
                .header("Content-Type", "application/json")
                .asJson();
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
