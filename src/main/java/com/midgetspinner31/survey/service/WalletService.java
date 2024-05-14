package com.midgetspinner31.survey.service;

import com.midgetspinner31.survey.dto.WalletInfo;
import com.midgetspinner31.survey.dto.WalletTransactionInfo;

import java.math.BigDecimal;
import java.util.List;

public interface WalletService {
    WalletInfo createWalletForUser(String userId);

    WalletInfo getWalletForCurrentUser();

    WalletInfo addMoneyToUserWallet(String userId, BigDecimal amount);

    WalletInfo subtractMoneyFromUserWallet(String userId, BigDecimal amount);

    boolean canSubtractMoneyFromUserWallet(String userId, BigDecimal amount);

    List<WalletTransactionInfo> getWalletTransactionsForCurrentUser();

    /**
     * Создать транзакцию о начислении средств
     *
     * @param userId  - пользователь которому будут начислены средства
     * @param payerId - пользователь с которого были списаны средства
     * @param amount  - сумма
     * @param reason  - причина
     */
    void makeCreditPayment(String userId, String payerId, BigDecimal amount, String reason);

    /**
     * Создать транзакцию о списании средств у пользователя
     *
     * @param userId - id пользователя
     * @param amount - сумма
     * @param reason - причина
     */
    void makeDebitPayment(String userId, BigDecimal amount, String reason);

    WalletInfo makeDepositPayment(String userId, BigDecimal amount, String reason);

}
