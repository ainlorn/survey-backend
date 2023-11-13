package com.midgetspinner31.survey.db.migration;

import com.midgetspinner31.survey.db.entity.SurveyAnswer;
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

@ChangeUnit(id = "init-survey-answer", order = "005", author = "sosmunk")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class M005_InitSurveyAnswer {
    MongoTemplate mongoTemplate;

    @BeforeExecution
    public void before() {
        mongoTemplate.createCollection(SurveyAnswer.class, CollectionOptions.empty()
                .validator(Validator.schema(MongoJsonSchema.builder()
                        .properties(
                                JsonSchemaProperty.string("survey_id"),
                                JsonSchemaProperty.int32("polling_time"),
                                JsonSchemaProperty.array("answers")
                                        .items(JsonSchemaObject.object(), JsonSchemaObject.nil())
                        ).required("survey_id", "polling_time", "answers")
                        .build())));
    }

    @RollbackBeforeExecution
    public void rollbackBefore() {
        mongoTemplate.dropCollection(SurveyAnswer.class);
    }

    @Execution
    public void execute() {
    }

    @RollbackExecution
    public void rollbackExecute() {
    }
}
