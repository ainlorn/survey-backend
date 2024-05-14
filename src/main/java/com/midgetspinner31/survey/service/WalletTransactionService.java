package com.midgetspinner31.survey.service;

import com.midgetspinner31.survey.db.entity.Wallet;
import com.midgetspinner31.survey.db.entity.WalletTransaction;

import java.math.BigDecimal;

public interface WalletTransactionService {

    WalletTransaction addCreditTransactionTo(Wallet wallet, BigDecimal amount, String payerId, String payeeId, String reason);

    WalletTransaction addDebitTransactionTo(Wallet wallet, BigDecimal amount, String reason);

    WalletTransaction addDepositTransactionTo(Wallet wallet, BigDecimal amount, String reason);

    WalletTransaction addWithdrawalTransactionTo(Wallet wallet, BigDecimal amount);

}
