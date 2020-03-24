package com.wallets.api.tests;

import com.wallets.api.client.WalletApi;
import com.wallets.api.exceptions.InvalidRequestException;
import com.wallets.api.exceptions.ServerErrorException;
import com.wallets.api.exceptions.UnauthorizedException;
import com.wallets.api.models.requests.BaseRequest;
import com.wallets.api.models.requests.self.BalanceRequest;
import com.wallets.api.models.requests.self.TransactionsRequest;
import com.wallets.api.models.requests.self.VerifyBvnRequest;
import com.wallets.api.models.requests.wallet.*;
import com.wallets.api.models.responses.ApiResponse;
import com.wallets.api.models.responses.Balance;
import com.wallets.api.models.responses.Response;
import com.wallets.api.models.responses.self.SelfTransactions;
import com.wallets.api.models.responses.self.VerifySelfBvn;
import com.wallets.api.models.responses.self.Wallet;
import com.wallets.api.models.responses.self.WalletTransaction;
import com.wallets.api.models.responses.wallet.AccountData;
import com.wallets.api.models.responses.wallet.CreditResponse;
import com.wallets.api.models.responses.wallet.DebitResponse;
import com.wallets.api.models.responses.wallet.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;

public class WalletApiTests {
    private WalletApi walletApi;
    @Before
    public void before(){
        walletApi = WalletApi.getInstance("uvjqzm5xl6bw", "hfucj5jatq8h", false);
    }
    @Test
    public void getBalanceTest() throws InvalidRequestException, ServerErrorException, UnauthorizedException, IOException {

        var balanceRequest = new BalanceRequest();

        balanceRequest.setCurrency("NGN");
        Balance balance = walletApi.getBalance(balanceRequest);

        Assert.assertTrue(balance.getWalletCurrency().equals("NGN"));
        Assert.assertTrue(balance.getWalletBalance() instanceof BigDecimal);
    }

    @Test
    public void getTransactionsForSelf() throws ServerErrorException, ParseException, InvalidRequestException, UnauthorizedException, IOException {
        var transactionsRequest = new TransactionsRequest();
        transactionsRequest.setCurrency("NGN");
        transactionsRequest.setDateFrom("2020-01-01");
        transactionsRequest.setDateTo("2020-03-18");
        transactionsRequest.setTransactionType(2);

        List<SelfTransactions> selfTransactions = walletApi.getTransactionsForSelf(transactionsRequest);

        Assert.assertTrue(selfTransactions.size() >= 0);
    }

    @Test
    public void verifyBvnForSelf() throws ServerErrorException, UnauthorizedException, InvalidRequestException, IOException {
        var bvnRequest = new VerifyBvnRequest();
        bvnRequest.setBvn("22231485915");
        bvnRequest.setDateOfBirth("14-04-1992");

        VerifySelfBvn selfBvn = walletApi.verifyBvnForSelf(bvnRequest);

        Assert.assertNotNull(selfBvn);
    }

    @Test
    public void getWallets() throws InvalidRequestException, ServerErrorException, UnauthorizedException, IOException {
        var baseRequest = new BaseRequest();

        List<Wallet> wallets = walletApi.getWallets(baseRequest);

        Assert.assertTrue(wallets.size() >= 0);
    }

    @Test
    public void getWalletToWalletTransactions() throws InvalidRequestException, ServerErrorException, UnauthorizedException, IOException {
        var transactionsRequest = new TransactionsRequest();
        transactionsRequest.setTransactionType(2);
        transactionsRequest.setDateFrom("2020-01-01");
        transactionsRequest.setDateTo("2020-03-18");
        transactionsRequest.setCurrency("NGN");

        List<WalletTransaction> walletTransactions = walletApi.getWalletToWalletTransactions(transactionsRequest);

        Assert.assertTrue(walletTransactions.size() >= 0);
    }

    @Test
    public void debitWallet() throws InvalidRequestException, ServerErrorException, UnauthorizedException, IOException {
        var debitRequest = new DebitWalletRequest();
        var amount = new BigDecimal(1.0);
        debitRequest.setAmount(amount);
        debitRequest.setPhoneNumber("08057998539");
        debitRequest.setTransactionReference(getTransactionRef());

        DebitResponse response = walletApi.debitWallet(debitRequest);

        Assert.assertTrue(response.getAmountCredited().longValue() == 1);
    }

    @Test
    public void creditWallet() throws InvalidRequestException, ServerErrorException, UnauthorizedException, IOException {
        var creditRequest = new CreditWalletRequest();
        var amount = new BigDecimal(1.0);
        creditRequest.setAmount(amount);
        creditRequest.setPhoneNumber("08057998539");
        creditRequest.setTransactionReference(getTransactionRef());

        CreditResponse response = walletApi.creditWallet(creditRequest);

        Assert.assertTrue(response.getAmountCredited().longValue() == 1);
    }

    @Test
    public void retrieveAccountNo() throws InvalidRequestException, ServerErrorException, UnauthorizedException, IOException {
        var retrieveAccountNoReq = new RetrieveAccountNoRequest();
        retrieveAccountNoReq.setPhoneNumber("08148657415");

        AccountData accountData = walletApi.retrieveWalletAccountNo(retrieveAccountNoReq);
        Assert.assertNotNull(accountData.getAccountNumber());
        Assert.assertNotNull(accountData.getAccountName());
        Assert.assertNotNull(accountData.getBank());
    }
    @Test
    public void setWalletPassword() throws InvalidRequestException, ServerErrorException, UnauthorizedException, IOException {
        var walletPasswordReq = new SetPasswordRequest();
        walletPasswordReq.setPassword("P@$$w0rd");
        walletPasswordReq.setPhoneNumber("08057998539");

        Response response = walletApi.setWalletPassword(walletPasswordReq);
        Assert.assertEquals(response.getResponseCode(), "200");
    }

    @Test
    public void getUser() throws InvalidRequestException, ServerErrorException, UnauthorizedException, IOException {
        var getUserRequest = new GetUserRequest();
        getUserRequest.setPhoneNumber("08057998539");

        User user = walletApi.getUser(getUserRequest);
        Assert.assertNotNull(user);
    }

    private String getTransactionRef() {
        double ref = Math.floor(Math.random() * (9 * Math.pow(10, 10 - 1))) + Math.pow(10, 10 - 1);
        return String.valueOf(ref);
    }


}
