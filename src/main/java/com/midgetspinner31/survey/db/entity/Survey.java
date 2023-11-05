package com.midgetspinner31.survey.db.entity;

import com.midgetspinner31.survey.dto.SurveyInfo;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

@Document("surveys")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Survey {
    @Id
    String id;

    String name;

    String description;
    @Field("survey_topics")
    List<String> surveyTopics;

    @Field("creator_id")
    String creatorId;

    @Field("creation_date")
    Date creationDate;

    List<Question> questions;

    //TODO: Вынести функционал to<T>Info в фабрику (create<T>from)
    public SurveyInfo toSurveyInfo() {
        return new SurveyInfo(
                name,
                description,
                surveyTopics,
                creatorId,
                creationDate,
                questions.stream().map(Question::toQuestionInfo).toList()
        );
    }
}
