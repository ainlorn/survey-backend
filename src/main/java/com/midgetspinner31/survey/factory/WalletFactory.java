package com.midgetspinner31.survey.factory;

import com.midgetspinner31.survey.db.entity.Wallet;
import com.midgetspinner31.survey.dto.WalletInfo;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;


@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WalletFactory {

    private WalletTransactionFactory walletTransactionFactory;

    public Wallet createWalletFrom(String userId) {
        return Wallet.builder()
                .balance(BigDecimal.ZERO)
                .userId(userId)
                .transactions(new ArrayList<>())
                .build();
    }

    public Wallet createWalletFrom(String userId, BigDecimal balance) {
        return Wallet.builder()
                .balance(balance)
                .userId(userId)
                .transactions(new ArrayList<>())
                .build();
    }

    public WalletInfo createWalletInfoFrom(Wallet wallet) {
        return WalletInfo.builder()
                .id(wallet.getId())
                .balance(wallet.getBalance())
                .transactions(wallet.getTransactions().stream()
                        .map(walletTransactionFactory::createWalletTransactionInfoFrom)
                        .toList()
                )
                .build();
    }
}
