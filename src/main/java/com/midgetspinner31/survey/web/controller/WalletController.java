package com.midgetspinner31.survey.web.controller;


import com.midgetspinner31.survey.dto.WalletInfo;
import com.midgetspinner31.survey.service.WalletService;
import com.midgetspinner31.survey.web.annotation.SurveyApiV1;
import com.midgetspinner31.survey.web.response.WalletInfoResponse;
import com.midgetspinner31.survey.web.response.WalletTransactionInfoResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@SurveyApiV1
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WalletController {
    WalletService walletService;

    /**
     * Получить данные о кошельке текущего пользователя
     */
    @GetMapping("/wallet")
    public WalletInfoResponse getCurrentWalletInfo() {
        return new WalletInfoResponse(walletService.getWalletForCurrentUser());
    }

    /**
     * Получить баланс текущего кошелька
     */
    @GetMapping("/wallet/balance")
    public BigDecimal getCurrentWalletBalance() {
        return walletService.getWalletForCurrentUser().getBalance();
    }


    /**
     * Добавить баланс для пользователя (тестовая среда)
     *
     * @param userId - id пользователя
     * @param amount - сумма в формате decimal128
     */
    @PostMapping("/wallet/test/add")
    public WalletInfo addMoneyToUser(@RequestParam String userId, @RequestParam BigDecimal amount) {
        return walletService.makeDepositPayment(userId, amount, "Пополнение кошелька");
    }

    /**
     * Получить транзакции пользователя
     */
    @GetMapping("/wallet/transactions")
    public WalletTransactionInfoResponse getCurrentWalletTransactions() {
        return new WalletTransactionInfoResponse(walletService.getWalletTransactionsForCurrentUser());
    }
}
