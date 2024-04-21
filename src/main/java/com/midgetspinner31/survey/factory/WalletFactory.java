package com.midgetspinner31.survey.factory;

import com.midgetspinner31.survey.db.entity.Wallet;
import com.midgetspinner31.survey.dto.WalletInfo;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WalletFactory {

    public Wallet createWalletFrom(String userId) {
        return Wallet.builder()
                .balance(BigDecimal.ZERO)
                .userId(userId)
                .build();
    }

    public Wallet createWalletFrom(String userId, BigDecimal balance) {
        return Wallet.builder()
                .balance(balance)
                .userId(userId)
                .build();
    }

    public WalletInfo createWalletInfoFrom(Wallet wallet) {
        return WalletInfo.builder()
                .id(wallet.getId())
                .balance(wallet.getBalance())
                .build();
    }
}
