package com.wallets.api.models.responses.self;

import com.wallets.api.models.responses.wallet.User;

import java.util.HashMap;
import java.util.Map;

public enum UserProgress {
    VerifyOTP(0),
    SetPassword(1),
    TakeASelfie(2),
    SetBVN(3),
    UploadId(4),
    Finished(5),
    VerifyEmail(6),
    VerifyPhoneNumber(7),
    PhoneNumberVerified(8);

    private int value;
    private static Map map = new HashMap<>();

    static {
        for (UserProgress userProgress : UserProgress.values()) {
            map.put(userProgress.value, userProgress);
        }
    }

    private UserProgress(int value) {
        this.value = value;
    }

    public static UserProgress valueOf(int userProgress) {
        return (UserProgress) map.get(userProgress);
    }

    public int getValue() {
        return value;
    }
}
