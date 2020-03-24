package com.wallets.api.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.wallets.api.exceptions.InvalidRequestException;
import com.wallets.api.exceptions.ServerErrorException;
import com.wallets.api.exceptions.UnauthorizedException;
import com.wallets.api.models.requests.BaseRequest;
import com.wallets.api.models.requests.account.ResolveBvnRequest;
import com.wallets.api.models.requests.airtime.AirtimePurchaseRequest;
import com.wallets.api.models.requests.self.BalanceRequest;
import com.wallets.api.models.requests.self.TransactionsRequest;
import com.wallets.api.models.requests.self.VerifyBvnRequest;
import com.wallets.api.models.requests.transfer.AccountEnquiryRequest;
import com.wallets.api.models.requests.transfer.TransferDetailsRequest;
import com.wallets.api.models.requests.wallet.*;
import com.wallets.api.models.responses.ApiResponse;
import com.wallets.api.models.responses.Balance;
import com.wallets.api.models.responses.Response;
import com.wallets.api.models.responses.account.BvnData;
import com.wallets.api.models.responses.airtime.AirtimeModel;
import com.wallets.api.models.responses.airtime.AirtimePurchaseResponse;
import com.wallets.api.models.responses.self.*;
import com.wallets.api.models.responses.transfer.Bank;
import com.wallets.api.models.responses.transfer.TransferDetails;
import com.wallets.api.models.responses.wallet.*;
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

    public DebitResponse debitWallet(DebitWalletRequest request) throws IOException, ServerErrorException, UnauthorizedException, InvalidRequestException {
        request.setSecretKey(secretKey);
        HttpResponse<String> response = post(WalletApiUrls.WalletUrls.DEBIT, request);

        if (response.getStatus() == 200) {
            ApiResponse<DebitResponse> result = getObjectMapper().readValue(response.getBody(), new TypeReference<ApiResponse<DebitResponse>>(){});
            return result.getData();
        }
        ThrowError(response);
        return null;
    }

    public CreditResponse creditWallet(CreditWalletRequest req) throws IOException, ServerErrorException, UnauthorizedException, InvalidRequestException {
        req.setSecretKey(secretKey);
        HttpResponse<String> res = post(WalletApiUrls.WalletUrls.CREDIT, req);

        if (res.getStatus() == 200) {
            ApiResponse<CreditResponse> result = getObjectMapper().readValue(res.getBody(), new TypeReference<ApiResponse<CreditResponse>>(){});
            return result.getData();
        }
        ThrowError(res);
        return null;
    }

    public WalletData createWallet(CreateWalletRequest req) throws IOException, ServerErrorException, UnauthorizedException, InvalidRequestException {
        req.setSecretKey(secretKey);
        HttpResponse<String> res = post(WalletApiUrls.WalletUrls.CREATE, req);
        if (res.getStatus() == 200) {
            ApiResponse<WalletData> result = getObjectMapper().readValue(res.getBody(), new TypeReference<ApiResponse<WalletData>>(){});
            return result.getData();
        }
        ThrowError(res);
        return null;
    }

    public WalletData generateWallet(GenerateWalletRequest req) throws IOException, ServerErrorException, UnauthorizedException, InvalidRequestException {
        req.setSecretKey(secretKey);
        HttpResponse<String> res = post(WalletApiUrls.WalletUrls.GENERATE, req);
        if (res.getStatus() == 200) {
            ApiResponse<WalletData> result = getObjectMapper().readValue(res.getBody(), new TypeReference<ApiResponse<WalletData>>(){});
            return result.getData();
        }
        ThrowError(res);
        return null;
    }

    public AccountData generateWalletAccountNo(GenerateAccountNoRequest req) throws IOException, ServerErrorException, UnauthorizedException, InvalidRequestException {
        req.setSecretKey(secretKey);
        HttpResponse<String> res = post(WalletApiUrls.WalletUrls.GENERATE_ACCOUNT_NO, req);
        if (res.getStatus() == 200) {
            ApiResponse<AccountData> result = getObjectMapper().readValue(res.getBody(), new TypeReference<ApiResponse<AccountData>>(){});
            return result.getData();
        }
        ThrowError(res);
        return null;
    }

    public AccountData retrieveWalletAccountNo(RetrieveAccountNoRequest req) throws IOException, ServerErrorException, UnauthorizedException, InvalidRequestException {
        req.setSecretKey(secretKey);
        HttpResponse<String> res = post(WalletApiUrls.WalletUrls.RETRIEVE_ACCOUNT_NO, req);
        if (res.getStatus() == 200) {
            ApiResponse<AccountData> result = getObjectMapper().readValue(res.getBody(), new TypeReference<ApiResponse<AccountData>>(){});
            return result.getData();
        }
        ThrowError(res);
        return null;
    }

    public Response setWalletPassword(SetPasswordRequest req) throws IOException, ServerErrorException, UnauthorizedException, InvalidRequestException {
        req.setSecretKey(secretKey);
        HttpResponse<String> res = post(WalletApiUrls.WalletUrls.SET_PASSWORD, req);
        if (res.getStatus() == 200) {
            Response result = getObjectMapper().readValue(res.getBody(), Response.class);
            return result;
        }
        ThrowError(res);
        return null;
    }

    public VerifySelfBvn verifyWalletBvn(VerifyBvnRequest req) throws IOException, ServerErrorException, UnauthorizedException, InvalidRequestException {
        req.setSecretKey(secretKey);
        HttpResponse<String> res = post(WalletApiUrls.WalletUrls.VERIFY, req);
        if (res.getStatus() == 200) {
            VerifySelfBvn result = getObjectMapper().readValue(res.getBody(), VerifySelfBvn.class);
            return result;
        }
        ThrowError(res);
        return null;
    }

    public User getUser(GetUserRequest req) throws IOException, ServerErrorException, UnauthorizedException, InvalidRequestException {
        req.setSecretKey(secretKey);
        HttpResponse<String> res = post(WalletApiUrls.WalletUrls.GET_WALLET, req);
        if (res.getStatus() == 200) {
            ApiResponse<User> result = getObjectMapper().readValue(res.getBody(), new TypeReference<ApiResponse<User>>(){});
            return result.getData();
        }
        ThrowError(res);
        return null;
    }

    public Balance getWalletBalance(WalletBalanceRequest req) throws IOException, ServerErrorException, UnauthorizedException, InvalidRequestException {
        req.setSecretKey(secretKey);
        HttpResponse<String> res = post(WalletApiUrls.WalletUrls.GET_BALANCE, req);
        if (res.getStatus() == 200) {
            ApiResponse<Balance> result = getObjectMapper().readValue(res.getBody(), new TypeReference<ApiResponse<Balance>>(){});
            return result.getData();
        }
        ThrowError(res);
        return null;
    }

    public List<Bank> getBanks() throws IOException, ServerErrorException, UnauthorizedException, InvalidRequestException {
        HttpResponse<String> res = post(WalletApiUrls.PayoutsUrls.GET_BANKS, null);
        if (res.getStatus() == 200) {
            List<Bank> result = getObjectMapper().readValue(res.getBody(), new TypeReference<List<Bank>>(){});
            return result;
        }
        ThrowError(res);
        return null;
    }

    public TransferDetails getTransferDetails(TransferDetailsRequest req) throws IOException, ServerErrorException, UnauthorizedException, InvalidRequestException {
        req.setSecretKey(secretKey);
        HttpResponse<String> res = post(WalletApiUrls.PayoutsUrls.TRANSACTION_DETAILS, req);
        if (res.getStatus() == 200) {
            TransferDetails result = getObjectMapper().readValue(res.getBody(), TransferDetails.class);
            return result;
        }
        ThrowError(res);
        return null;
    }

    public AccountData getAccountData(AccountEnquiryRequest req) throws IOException, ServerErrorException, UnauthorizedException, InvalidRequestException {
        req.setSecretKey(secretKey);
        HttpResponse<String> res = post(WalletApiUrls.PayoutsUrls.ACCOUNT_ENQUIRY, req);
        if (res.getStatus() == 200) {
            AccountData result = getObjectMapper().readValue(res.getBody(), AccountData.class);
            return result;
        }
        ThrowError(res);
        return null;
    }

    public AirtimeModel getAirtimeProviders() throws IOException, ServerErrorException, UnauthorizedException, InvalidRequestException {
        HttpResponse<String> res = post(WalletApiUrls.AirtimeUrls.PROVIDERS, null);
        if (res.getStatus() == 200) {
            AirtimeModel result = getObjectMapper().readValue(res.getBody(), AirtimeModel.class);
            return result;
        }
        ThrowError(res);
        return null;
    }

    public AirtimePurchaseResponse airtimePurchase(AirtimePurchaseRequest req) throws IOException, ServerErrorException, UnauthorizedException, InvalidRequestException {
        req.setSecretKey(secretKey);
        HttpResponse<String> res = post(WalletApiUrls.AirtimeUrls.PURCHASE, req);
        if (res.getStatus() == 200) {
            AirtimePurchaseResponse result = getObjectMapper().readValue(res.getBody(), AirtimePurchaseResponse.class);
            return result;
        }
        ThrowError(res);
        return null;
    }

    public BvnData resolveBvn(ResolveBvnRequest req) throws IOException, ServerErrorException, UnauthorizedException, InvalidRequestException {
        req.setSecretKey(secretKey);
        HttpResponse<String> res = post(WalletApiUrls.IdentityUrls.RESOLVE_BVN, req);
        if (res.getStatus() == 200) {
            BvnData result = getObjectMapper().readValue(res.getBody(), BvnData.class);
            return result;
        }
        ThrowError(res);
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
        return new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .setPropertyNamingStrategy(PropertyNamingStrategy.UPPER_CAMEL_CASE);
    }
}
