package com.midgetspinner31.survey.db.migration;

import com.midgetspinner31.survey.db.entity.Wallet;
import io.mongock.api.annotations.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.schema.JsonSchemaProperty;
import org.springframework.data.mongodb.core.schema.MongoJsonSchema;
import org.springframework.data.mongodb.core.validation.Validator;

import java.util.Map;

@ChangeUnit(id = "update-wallet-schema", order = "011", author = "sosmunk")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class M011_UpdateWalletSchema {
    MongoTemplate mongoTemplate;

    @BeforeExecution
    public void before() {
        mongoTemplate.executeCommand(new Document(Map.of(
                "collMod", mongoTemplate.getCollectionName(Wallet.class),
                "validator", Validator.schema(MongoJsonSchema.builder()
                        .properties(
                                JsonSchemaProperty.string("user_id"),
                                JsonSchemaProperty.decimal128("balance"),
                                JsonSchemaProperty.array("transactions")
                        ).required("user_id", "balance")
                        .build()).toDocument())));
    }

    @RollbackBeforeExecution
    public void rollbackBefore() {
    }

    @Execution
    public void execute() {
    }

    @RollbackExecution
    public void rollbackExecute() {
    }
}
