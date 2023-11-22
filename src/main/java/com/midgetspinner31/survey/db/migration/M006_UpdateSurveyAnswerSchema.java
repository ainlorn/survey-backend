package com.midgetspinner31.survey.db.migration;

import com.midgetspinner31.survey.db.entity.SurveyAnswer;
import io.mongock.api.annotations.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.bson.Document;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.schema.JsonSchemaObject;
import org.springframework.data.mongodb.core.schema.JsonSchemaProperty;
import org.springframework.data.mongodb.core.schema.MongoJsonSchema;
import org.springframework.data.mongodb.core.validation.Validator;

import java.util.Map;

@ChangeUnit(id = "update-survey-answer-schema", order = "006", author = "ainlorn")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class M006_UpdateSurveyAnswerSchema {
    MongoTemplate mongoTemplate;

    @BeforeExecution
    public void before() {
        mongoTemplate.executeCommand(new Document(Map.of(
                "collMod", mongoTemplate.getCollectionName(SurveyAnswer.class),
                "validator", Validator.schema(MongoJsonSchema.builder()
                        .properties(
                                JsonSchemaProperty.string("survey_id"),
                                JsonSchemaProperty.int32("polling_time"),
                                JsonSchemaProperty.array("answers")
                        ).required("survey_id", "polling_time", "answers")
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
