package com.midgetspinner31.survey.db.migration;

import com.midgetspinner31.survey.db.entity.Survey;
import com.midgetspinner31.survey.db.migration.validator.ValidatorService;
import io.mongock.api.annotations.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.MongoTemplate;

@ChangeUnit(id = "init-survey", order = "003", author = "sosmunk")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class M003_InitSurvey {
    MongoTemplate mongoTemplate;
    ValidatorService validatorService;
    @BeforeExecution
    public void before() {
        mongoTemplate.createCollection(Survey.class, CollectionOptions.empty()
                .validator(validatorService.getSurveyValidator()));
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
