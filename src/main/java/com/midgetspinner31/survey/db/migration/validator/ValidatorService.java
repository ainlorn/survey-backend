package com.midgetspinner31.survey.db.migration.validator;

import org.springframework.data.mongodb.core.schema.JsonSchemaObject;
import org.springframework.data.mongodb.core.schema.JsonSchemaProperty;
import org.springframework.data.mongodb.core.schema.MongoJsonSchema;
import org.springframework.data.mongodb.core.validation.Validator;
import org.springframework.stereotype.Component;

@Component
public class ValidatorService {
    public Validator getSurveyValidator() {
        return Validator.schema(MongoJsonSchema.builder()
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
                .build());
    }
}
