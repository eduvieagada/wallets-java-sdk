package com.wallets.api.tests;

import com.wallets.api.client.WalletApi;
import com.wallets.api.exceptions.InvalidRequestException;
import com.wallets.api.exceptions.ServerErrorException;
import com.wallets.api.exceptions.UnauthorizedException;
import com.wallets.api.models.requests.self.BalanceRequest;
import com.wallets.api.models.responses.Balance;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class WalletApiTests {
    @Test
    public void getBalanceTest() throws InvalidRequestException, ServerErrorException, UnauthorizedException {
        WalletApi walletApi = WalletApi.getInstance("uvjqzm5xl6bw", "hfucj5jatq8h", false);

        var balanceRequest = new BalanceRequest();

        balanceRequest.setCurrency("NGN");
        Balance balance = walletApi.getBalance(balanceRequest);

        Assert.assertTrue(balance.getWalletCurrency().equals("NGN"));
        Assert.assertTrue(balance.getWalletBalance() instanceof BigDecimal);
    }
}
