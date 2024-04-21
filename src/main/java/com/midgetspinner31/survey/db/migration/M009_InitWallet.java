package com.midgetspinner31.survey.db.migration;

import com.midgetspinner31.survey.db.dao.WalletRepository;
import com.midgetspinner31.survey.db.entity.Wallet;
import com.midgetspinner31.survey.factory.WalletFactory;
import io.mongock.api.annotations.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.schema.JsonSchemaProperty;
import org.springframework.data.mongodb.core.schema.MongoJsonSchema;
import org.springframework.data.mongodb.core.validation.Validator;

@ChangeUnit(id = "init-wallet", order = "009", author = "sosmunk")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class M009_InitWallet {
    MongoTemplate mongoTemplate;

    WalletRepository walletRepository;
    WalletFactory walletFactory;

    @BeforeExecution
    public void before() {
        mongoTemplate.createCollection(Wallet.class, CollectionOptions.empty()
                .validator(Validator.schema(MongoJsonSchema.builder()
                        .properties(
                                JsonSchemaProperty.string("user_id"),
                                JsonSchemaProperty.decimal128("balance")
                        ).required("user_id", "balance")
                        .build())));
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