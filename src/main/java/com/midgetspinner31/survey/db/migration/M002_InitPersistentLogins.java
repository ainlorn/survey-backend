package com.midgetspinner31.survey.db.migration;

import com.midgetspinner31.survey.db.entity.PersistentLogin;
import com.mongodb.client.model.IndexOptions;
import io.mongock.api.annotations.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.bson.Document;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.schema.JsonSchemaProperty;
import org.springframework.data.mongodb.core.schema.MongoJsonSchema;
import org.springframework.data.mongodb.core.validation.Validator;

@ChangeUnit(id = "init-persistent-logins", order = "002", author = "ainlorn")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class M002_InitPersistentLogins {
    MongoTemplate mongoTemplate;

    @BeforeExecution
    public void before() {
        var userCollection = mongoTemplate.createCollection(PersistentLogin.class, CollectionOptions.empty()
                .validator(Validator.schema(MongoJsonSchema.builder()
                        .required("username", "series", "token", "lastUsed")
                        .properties(
                                JsonSchemaProperty.string("username"),
                                JsonSchemaProperty.string("series"),
                                JsonSchemaProperty.string("token"),
                                JsonSchemaProperty.date("lastUsed")).build())));
        userCollection.createIndex(new Document("series", 1), new IndexOptions().name("series").unique(true));
        userCollection.createIndex(new Document("username", 1), new IndexOptions().name("username"));
    }

    @RollbackBeforeExecution
    public void rollbackBefore() {
        mongoTemplate.dropCollection(PersistentLogin.class);
    }

    @Execution
    public void execute() {
    }

    @RollbackExecution
    public void rollbackExecute() {
    }
}
