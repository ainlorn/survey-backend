package com.midgetspinner31.survey.db.migration;

import com.midgetspinner31.survey.db.entity.Rating;
import com.midgetspinner31.survey.enumerable.InterviewResult;
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

import java.util.Arrays;
import java.util.Map;

@ChangeUnit(id = "init-rating", order = "011", author = "ainlorn")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class M011_InitRating {
    MongoTemplate mongoTemplate;

    @BeforeExecution
    public void before() {
        var ratingCollection = mongoTemplate.createCollection(Rating.class, CollectionOptions.empty()
                .validator(Validator.schema(MongoJsonSchema.builder()
                        .properties(
                                JsonSchemaProperty.string("slotId"),
                                JsonSchemaProperty.string("targetUserId"),
                                JsonSchemaProperty.string("sourceUserId"),
                                JsonSchemaProperty.string("interviewResult")
                                        .possibleValues(
                                                Arrays.stream(InterviewResult.values())
                                                        .map(InterviewResult::name)
                                                        .toList()
                                        ),
                                JsonSchemaProperty.int32("rating"),
                                JsonSchemaProperty.string("comment")
                        ).required("slotId", "targetUserId", "sourceUserId", "interviewResult", "rating", "comment")
                        .build())));
        ratingCollection.createIndex(new Document("slotId", 1), new IndexOptions());
        ratingCollection.createIndex(new Document("targetUserId", 1), new IndexOptions());
        ratingCollection.createIndex(
                new Document(Map.of("slotId", 1, "sourceUserId", 1)), new IndexOptions().unique(true));
    }

    @RollbackBeforeExecution
    public void rollbackBefore() {
        mongoTemplate.dropCollection(Rating.class);
    }

    @Execution
    public void execute() {
    }

    @RollbackExecution
    public void rollbackExecute() {
    }
}
