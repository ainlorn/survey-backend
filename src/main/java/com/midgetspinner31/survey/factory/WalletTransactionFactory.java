package com.midgetspinner31.survey.factory;

import com.midgetspinner31.survey.db.entity.WalletTransaction;
import com.midgetspinner31.survey.dto.WalletTransactionInfo;
import com.midgetspinner31.survey.enumerable.TransactionType;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WalletTransactionFactory {
    public WalletTransaction createCreditTransactionFrom(BigDecimal amount, String payerId, String payeeId, String reason) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Попытка начислить отрицательную сумму средств");
        }
        return WalletTransaction.builder()
                .type(TransactionType.CREDIT)
                .createdAt(LocalDateTime.now())
                .amount(amount)
                .payerId(payerId)
                .payeeId(payeeId)
                .reason(reason)
                .build();
    }

    public WalletTransaction createDebitTransactionFrom(BigDecimal amount, String reason) {
        if (amount.compareTo(BigDecimal.ZERO) > 0) {
            throw new IllegalArgumentException("Попытка указать положительную сумму списания");
        }

        return WalletTransaction.builder()
                .type(TransactionType.DEBIT)
                .createdAt(LocalDateTime.now())
                .amount(amount)
                .reason(reason)
                .build();
    }

    public WalletTransaction createDepositTransactionFrom(BigDecimal amount, String reason) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Попытка начислить отрицательную сумму средств");
        }

        return WalletTransaction.builder()
                .type(TransactionType.DEPOSIT)
                .createdAt(LocalDateTime.now())
                .reason(reason)
                .amount(amount)
                .build();
    }

    public WalletTransaction createWithdrawalTransactionFrom(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) > 0) {
            throw new IllegalArgumentException("Попытка указать положительную сумму списания");
        }

        return WalletTransaction.builder()
                .type(TransactionType.WITHDRAWAL)
                .createdAt(LocalDateTime.now())
                .amount(amount)
                .build();
    }

    public WalletTransactionInfo createWalletTransactionInfoFrom(WalletTransaction walletTransaction) {
        return WalletTransactionInfo.builder()
                .type(walletTransaction.getType())
                .createdAt(walletTransaction.getCreatedAt())
                .amount(walletTransaction.getAmount())
                .reason(walletTransaction.getReason())
                .build();
    }
}
