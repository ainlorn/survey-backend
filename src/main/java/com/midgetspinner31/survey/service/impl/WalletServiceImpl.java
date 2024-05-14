package com.midgetspinner31.survey.service.impl;


import com.midgetspinner31.survey.db.dao.UserRepository;
import com.midgetspinner31.survey.db.dao.WalletRepository;
import com.midgetspinner31.survey.db.entity.Wallet;
import com.midgetspinner31.survey.dto.WalletInfo;
import com.midgetspinner31.survey.dto.WalletTransactionInfo;
import com.midgetspinner31.survey.exception.WalletNotFoundException;
import com.midgetspinner31.survey.factory.WalletFactory;
import com.midgetspinner31.survey.service.WalletService;
import com.midgetspinner31.survey.service.WalletTransactionService;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    WalletRepository walletRepository;
    WalletFactory walletFactory;
    WalletTransactionService walletTransactionService;
    UserRepository userRepository;

    @Override
    public WalletInfo createWalletForUser(String userId) {
        Wallet wallet = walletFactory.createWalletFrom(userId);
        walletRepository.save(wallet);
        return walletFactory.createWalletInfoFrom(wallet);
    }

    @PreAuthorize("isAuthenticated()")
    @Override
    public WalletInfo getWalletForCurrentUser() {
        return walletFactory.createWalletInfoFrom(
                getWalletForUser(userRepository.getCurrentUser().getId())
        );
    }

    private Wallet getWalletForUser(String userId) {
        Wallet wallet = walletRepository.findWalletByUserId(userId);

        if (wallet == null) {
            throw new WalletNotFoundException();
        }
        return wallet;
    }

    private BigDecimal subtractMoneyFromWallet(Wallet wallet, BigDecimal amount) {
        wallet.setBalance(wallet.getBalance().subtract(amount));

        walletRepository.save(wallet);

        return wallet.getBalance();
    }

    private boolean canSubtractMoneyFromWallet(@NotNull Wallet wallet, BigDecimal amount) {
        BigDecimal balance = wallet.getBalance();

        return balance.subtract(amount).compareTo(BigDecimal.ZERO) > 0;
    }

    @Override
    public boolean canSubtractMoneyFromUserWallet(String userId, BigDecimal amount) {
        Wallet wallet = walletRepository.findWalletByUserId(userId);

        return canSubtractMoneyFromWallet(wallet, amount);
    }

    @Override
    public void makeCreditPayment(String userId, String payerId, BigDecimal amount, String reason) {
        Wallet wallet = getWalletForUser(userId);
        addMoneyToWallet(wallet, amount);
        walletTransactionService.addCreditTransactionTo(wallet, amount, payerId, userId, reason);
    }

    @Override
    public void makeDebitPayment(String userId, BigDecimal amount, String reason) {
        Wallet wallet = getWalletForUser(userId);
        subtractMoneyFromWallet(wallet, amount);
        walletTransactionService.addDebitTransactionTo(wallet, amount, reason);
    }

    @Override
    public WalletInfo makeDepositPayment(String userId, BigDecimal amount, String reason) {
        Wallet wallet = getWalletForUser(userId);
        addMoneyToWallet(wallet, amount);
        walletTransactionService.addDepositTransactionTo(wallet, amount, reason);
        return walletFactory.createWalletInfoFrom(wallet);
    }

    private BigDecimal addMoneyToWallet(@NotNull Wallet wallet, BigDecimal amount) {
        wallet.setBalance(wallet.getBalance().add(amount));

        walletRepository.save(wallet);

        return wallet.getBalance();
    }

    @Override
    public WalletInfo addMoneyToUserWallet(String userId, BigDecimal amount) {
        Wallet wallet = getWalletForUser(userId);

        addMoneyToWallet(wallet, amount);

        return walletFactory.createWalletInfoFrom(wallet);
    }

    @Override
    public WalletInfo subtractMoneyFromUserWallet(String userId, BigDecimal amount) {
        Wallet wallet = getWalletForUser(userId);

        subtractMoneyFromWallet(wallet, amount);

        return walletFactory.createWalletInfoFrom(wallet);
    }

    @Override
    public List<WalletTransactionInfo> getWalletTransactionsForCurrentUser() {
        return getWalletForCurrentUser().getTransactions();
    }
}
