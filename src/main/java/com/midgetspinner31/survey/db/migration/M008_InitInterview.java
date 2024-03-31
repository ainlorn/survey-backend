package com.midgetspinner31.survey.db.migration;

import com.midgetspinner31.survey.db.entity.Interview;
import com.midgetspinner31.survey.db.entity.InterviewSlot;
import io.mongock.api.annotations.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.schema.JsonSchemaObject;
import org.springframework.data.mongodb.core.schema.JsonSchemaProperty;
import org.springframework.data.mongodb.core.schema.MongoJsonSchema;
import org.springframework.data.mongodb.core.validation.Validator;

@ChangeUnit(id = "init-interview", order = "008", author = "ainlorn")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class M008_InitInterview {
    MongoTemplate mongoTemplate;

    @BeforeExecution
    public void before() {
        mongoTemplate.createCollection(Interview.class, CollectionOptions.empty()
                .validator(Validator.schema(MongoJsonSchema.builder()
                        .properties(
                                JsonSchemaProperty.string("name"),
                                JsonSchemaProperty.string("description"),
                                JsonSchemaProperty.array("interviewTopics").items(JsonSchemaObject.string()),
                                JsonSchemaProperty.string("creatorId"),
                                JsonSchemaProperty.date("creationDate"),
                                JsonSchemaProperty.date("startDate"),
                                JsonSchemaProperty.date("endDate"),
                                JsonSchemaProperty.object("respondentRestrictions")
                        ).required("name", "interviewTopics", "creatorId", "creationDate",
                                "startDate", "endDate", "respondentRestrictions")
                        .build())));
        mongoTemplate.createCollection(InterviewSlot.class, CollectionOptions.empty()
                .validator(Validator.schema(MongoJsonSchema.builder()
                        .properties(
                                JsonSchemaProperty.string("interviewId"),
                                JsonSchemaProperty.string("respondentId"),
                                JsonSchemaProperty.date("startDate"),
                                JsonSchemaProperty.date("endDate")
                        ).required("interviewId", "startDate", "endDate")
                        .build())));
    }

    @RollbackBeforeExecution
    public void rollbackBefore() {
        mongoTemplate.dropCollection(Interview.class);
        mongoTemplate.dropCollection(InterviewSlot.class);
    }

    @Execution
    public void execute() {
    }

    @RollbackExecution
    public void rollbackExecute() {
    }
}
