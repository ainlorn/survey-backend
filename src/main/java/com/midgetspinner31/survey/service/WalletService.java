package com.midgetspinner31.survey.service;

import com.midgetspinner31.survey.dto.WalletInfo;

import java.math.BigDecimal;

public interface WalletService {
    WalletInfo createWalletForUser(String userId);

    WalletInfo getWalletForCurrentUser();

    WalletInfo addMoneyToUserWallet(String userId, BigDecimal amount);

    WalletInfo subtractMoneyFromUserWallet(String userId, BigDecimal amount);

    boolean canSubtractMoneyFromUserWallet(String userId, BigDecimal amount);

}
