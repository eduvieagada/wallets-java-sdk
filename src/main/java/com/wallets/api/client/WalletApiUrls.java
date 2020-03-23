package com.wallets.api.client;

public final class WalletApiUrls {
    public static final String LIVE_BASE_URL = "https://api.wallets.africa";
    public static final String TEST_BASE_URL = "https://sandbox.wallets.africa";

    public static final class SelfUrls {
        public static final String BALANCE = "/self/balance";
        public static final String TRANSACTIONS = "/self/transactions";
        public static final String VERIFY_BVN = "/self/verifybvn";
        public static final String GET_WALLETS = "/self/users";
        public static final String WALLET_TRANSACTIONS = "/self/wallet/transactions";
    }

    public static final class WalletUrls {
        public static final String DEBIT = "/wallet/debit";
        public static final String CREDIT = "/wallet/credit";
        public static final String CREATE = "/wallet/create";
        public static final String VERIFY = "/wallet/verify";
        public static final String GENERATE = "/wallet/generate";
        public static final String GENERATE_ACCOUNT_NO = "/wallet/generateaccountnumber";
        public static final String RETRIEVE_ACCOUNT_NO = "/wallet/nuban";
        public static final String SET_PASSWORD = "/wallet/password";
        public static final String TRANSACTIONS = "/wallet/transactions";
        public static final String VERIFY_BVN = "/wallet/verifybvn";
        public static final String GET_WALLET = "/wallet/getuser";
        public static final String GET_BALANCE = "/wallet/balance";
    }

    public static final class PayoutsUrls {
        public static final String TRANSACTION_DETAILS = "/transfer/bank/details";
        public static final String BANK_TRANSFER = "/transfer/bank/account";
        public static final String ACCOUNT_ENQUIRY = "/transfer/bank/account/enquire";
        public static final String GET_BANKS = "/transfer/banks/all";
    }

    public static final class AirtimeUrls {
        public static final String PROVIDERS = "/bills/airtime/providers";
        public static final String PURCHASE = "/bills/airtime/purchase";
    }

    public static final class IdentityUrls {
        public static final String RESOLVE_BVN = "/account/resolvebvn";
    }
}
