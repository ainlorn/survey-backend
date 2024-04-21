package com.midgetspinner31.survey.db.migration;

import com.midgetspinner31.survey.db.dao.WalletRepository;
import com.midgetspinner31.survey.db.entity.User;
import com.midgetspinner31.survey.db.entity.Wallet;
import com.midgetspinner31.survey.factory.WalletFactory;
import io.mongock.api.annotations.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@ChangeUnit(id = "add-wallets", order = "010", author = "sosmunk")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class M010_AddWallets {
    MongoTemplate mongoTemplate;

    WalletRepository walletRepository;
    WalletFactory walletFactory;

    @BeforeExecution
    public void before() {

        List<String> userIds = mongoTemplate.findAll(User.class).stream().map(User::getId).toList();

        List<Wallet> wallets = new ArrayList<>();

        for (String userId : userIds) {
            Wallet wallet = new Wallet();
            wallet.setUserId(userId);
            wallet.setBalance(new BigDecimal(0));
            wallets.add(wallet);
        }

        walletRepository.saveAll(wallets);
    }

    @RollbackBeforeExecution
    public void rollbackBefore() {
        mongoTemplate.dropCollection(Wallet.class);
    }


    @Execution
    public void execute() {
    }

    @RollbackExecution
    public void rollbackExecute() {
    }
}