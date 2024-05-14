package com.midgetspinner31.survey.service.impl;


import com.midgetspinner31.survey.db.dao.WalletRepository;
import com.midgetspinner31.survey.db.entity.Wallet;
import com.midgetspinner31.survey.db.entity.WalletTransaction;
import com.midgetspinner31.survey.factory.WalletTransactionFactory;
import com.midgetspinner31.survey.service.WalletTransactionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class WalletTransactionServiceImpl implements WalletTransactionService {

    private final WalletTransactionFactory walletTransactionFactory;
    private final WalletRepository walletRepository;

    @Override
    public WalletTransaction addCreditTransactionTo(Wallet wallet, BigDecimal amount, String payerId, String payeeId, String reason) {
        WalletTransaction transaction = walletTransactionFactory.createCreditTransactionFrom(amount, payerId, payeeId, reason);
        saveTransaction(transaction, wallet);
        return transaction;
    }

    @Override
    public WalletTransaction addDebitTransactionTo(Wallet wallet, BigDecimal amount, String reason) {
        WalletTransaction transaction = walletTransactionFactory.createDebitTransactionFrom(amount, reason);
        saveTransaction(transaction, wallet);
        return transaction;
    }

    @Override
    public WalletTransaction addDepositTransactionTo(Wallet wallet, BigDecimal amount, String reason) {
        WalletTransaction transaction = walletTransactionFactory.createDepositTransactionFrom(amount, reason);
        saveTransaction(transaction, wallet);
        return transaction;
    }

    @Override
    public WalletTransaction addWithdrawalTransactionTo(Wallet wallet, BigDecimal amount) {
        WalletTransaction transaction = walletTransactionFactory.createWithdrawalTransactionFrom(amount);
        saveTransaction(transaction, wallet);
        return transaction;
    }

    private void saveTransaction(WalletTransaction transaction, Wallet wallet) {
        List<WalletTransaction> walletTransactions = wallet.getTransactions();
        if (walletTransactions == null) {
            walletTransactions = new ArrayList<>();
        }
        walletTransactions.add(transaction);
        wallet.setTransactions(walletTransactions);
        walletRepository.save(wallet);
    }
}
