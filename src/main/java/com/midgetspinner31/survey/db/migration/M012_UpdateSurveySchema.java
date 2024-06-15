package com.midgetspinner31.survey.db.migration;

import com.midgetspinner31.survey.db.entity.Survey;
import io.mongock.api.annotations.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

@ChangeUnit(id = "update-survey-schema", order = "012", author = "sosmunk")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class M012_UpdateSurveySchema {
    MongoTemplate mongoTemplate;

    @BeforeExecution
    public void before() {

        //TODO: Починить - ломается т.к не видит валидатор
//        mongoTemplate.executeCommand(new Document(Map.of(
//                "collMod", mongoTemplate.getCollectionName(Survey.class),
//                "validator", Validator.schema(MongoJsonSchema.builder()
//                        .properties(
//                                JsonSchemaProperty.int32("attemptsLeft")
//                        ).required("attemptsLeft")
//                        .build()).toDocument())));

        List<Survey> surveys = mongoTemplate.findAll(Survey.class);

        surveys.forEach(survey -> {
            survey.setAttemptsLeft(10);
            mongoTemplate.save(survey);
        });
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
