package com.midgetspinner31.survey.db.migration;

import com.midgetspinner31.survey.db.entity.Survey;
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

@ChangeUnit(id = "init-survey", order = "003", author = "sosmunk")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class M003_InitSurvey {
    MongoTemplate mongoTemplate;
    @BeforeExecution
    public void before() {
        mongoTemplate.createCollection(Survey.class, CollectionOptions.empty()
                .validator(Validator.schema(MongoJsonSchema.builder()
                        .properties(
                                JsonSchemaProperty.string("name"),
                                JsonSchemaProperty.string("description"),
                                JsonSchemaProperty.array("survey_topics").items(JsonSchemaObject.string()),
                                JsonSchemaProperty.string("creator_id"),
                                JsonSchemaProperty.date("creation_date"),
                                JsonSchemaProperty.array("questions")
                                        .items(JsonSchemaObject.object().properties(
                                                JsonSchemaProperty.string("text"),
                                                JsonSchemaProperty.bool("required"),
                                                JsonSchemaProperty.string("answer_type")
                                                        .possibleValues("text", "single_choice", "multiple_choice", "slider"),
                                                JsonSchemaProperty.object("restrictions")
                                                        .properties(
                                                                JsonSchemaProperty.number("min"),
                                                                JsonSchemaProperty.number("max"),
                                                                JsonSchemaProperty.int32("max_length"),
                                                                JsonSchemaProperty.array("choices")
                                                                        .items(JsonSchemaObject.string())
                                                               // TODO: constraints
                                                               ).required()
                                                ).required("text", "required", "answer_type"))
                        ).required("name", "survey_topics", "creator_id", "creation_date")
                        .build())));
    }

    @RollbackBeforeExecution
    public void rollbackBefore() {
        mongoTemplate.dropCollection(Survey.class);
    }

    @Execution
    public void execute() {
    }

    @RollbackExecution
    public void rollbackExecute() {
    }
}
