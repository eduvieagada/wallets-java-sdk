package com.wallets.api.tests;

import com.wallets.api.client.WalletApi;
import com.wallets.api.exceptions.InvalidRequestException;
import com.wallets.api.exceptions.ServerErrorException;
import com.wallets.api.exceptions.UnauthorizedException;
import com.wallets.api.models.requests.self.BalanceRequest;
import com.wallets.api.models.requests.self.TransactionsRequest;
import com.wallets.api.models.responses.Balance;
import com.wallets.api.models.responses.self.SelfTransactions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

public class WalletApiTests {
    private WalletApi walletApi;
    @Before
    public void before(){
        walletApi = WalletApi.getInstance("uvjqzm5xl6bw", "hfucj5jatq8h", false);
    }
    @Test
    public void getBalanceTest() throws InvalidRequestException, ServerErrorException, UnauthorizedException {

        var balanceRequest = new BalanceRequest();

        balanceRequest.setCurrency("NGN");
        Balance balance = walletApi.getBalance(balanceRequest);

        Assert.assertTrue(balance.getWalletCurrency().equals("NGN"));
        Assert.assertTrue(balance.getWalletBalance() instanceof BigDecimal);
    }

    @Test
    public void getTransactionsForSelf() throws ServerErrorException, ParseException, InvalidRequestException, UnauthorizedException {
        var transactionsRequest = new TransactionsRequest();
        transactionsRequest.setCurrency("NGN");
        transactionsRequest.setDateFrom("2020-01-01");
        transactionsRequest.setDateTo("2020-03-18");
        transactionsRequest.setTransactionType(2);

        List<SelfTransactions> selfTransactions = walletApi.getTransactionsForSelf(transactionsRequest);

        Assert.assertTrue(selfTransactions.size() > 0);
    }
}
