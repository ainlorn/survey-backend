package com.midgetspinner31.survey.db.migration;

import com.midgetspinner31.survey.db.entity.User;
import com.mongodb.client.model.IndexOptions;
import io.mongock.api.annotations.*;
import io.mongock.utils.field.Field;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.bson.Document;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.schema.JsonSchemaProperty;
import org.springframework.data.mongodb.core.schema.MongoJsonSchema;
import org.springframework.data.mongodb.core.validation.Validator;

@ChangeUnit(id = "init-users", order = "001", author = "ainlorn")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class M001_InitUsers {
    MongoTemplate mongoTemplate;

    @BeforeExecution
    public void before() {
        var userCollection = mongoTemplate.createCollection(User.class, CollectionOptions.empty()
                .validator(Validator.schema(MongoJsonSchema.builder()
                        .required("firstName", "lastName", "email", "phoneNumber", "password")
                        .properties(
                                JsonSchemaProperty.string("firstName"),
                                JsonSchemaProperty.string("lastName"),
                                JsonSchemaProperty.string("email"),
                                JsonSchemaProperty.string("phoneNumber"),
                                JsonSchemaProperty.string("password")).build())));
        userCollection.createIndex(new Document("email", 1), new IndexOptions().name("email").unique(true));
        userCollection.createIndex(new Document("phoneNumber", 1), new IndexOptions().name("phoneNumber").unique(true));
    }

    @RollbackBeforeExecution
    public void rollbackBefore() {
        mongoTemplate.dropCollection(User.class);
    }

    @Execution
    public void execute() {
    }

    @RollbackExecution
    public void rollbackExecute() {
    }
}
