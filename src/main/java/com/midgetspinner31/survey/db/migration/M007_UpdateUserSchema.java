package com.midgetspinner31.survey.db.migration;

import com.midgetspinner31.survey.db.entity.User;
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

@ChangeUnit(id = "update-user-schema", order = "007", author = "ainlorn")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class M007_UpdateUserSchema {
    MongoTemplate mongoTemplate;

    @BeforeExecution
    public void before() {
        mongoTemplate.executeCommand(new Document(Map.of(
                "collMod", mongoTemplate.getCollectionName(User.class),
                "validator", Validator.schema(MongoJsonSchema.builder()
                        .required("accountType", "email", "phoneNumber", "password", "additionalDetails")
                        .properties(
                                JsonSchemaProperty.string("accountType")
                                        .possibleValues("respondent", "survey_creator"),
                                JsonSchemaProperty.string("email"),
                                JsonSchemaProperty.string("phoneNumber"),
                                JsonSchemaProperty.string("password"),
                                JsonSchemaProperty.object("additionalDetails")).build()).toDocument())));
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
