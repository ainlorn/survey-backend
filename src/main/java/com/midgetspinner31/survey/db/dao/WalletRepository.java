package com.midgetspinner31.survey.db.dao;

import com.midgetspinner31.survey.db.entity.Wallet;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WalletRepository extends MongoRepository<Wallet, String> {
    Wallet findWalletByUserId(String userId);
}
