package com.midgetspinner31.survey.db.entity;

import com.midgetspinner31.survey.dto.SurveyInfo;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

/**
 * Опрос
 */
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

    /**
     * Название опроса
     */
    String name;

    /**
     * Описание опроса
     */
    String description;

    /**
     * Список тем опроса
     */
    @Field("survey_topics")
    List<String> surveyTopics;

    /**
     * id создателя опроса
     */
    //TODO: брать из сессии
    @Field("creator_id")
    String creatorId;

    /**
     * Дата создания опроса
     */
    //TODO: создавать при сохранении
    @Field("creation_date")
    Date creationDate;

    /**
     * Список вопросов
     */
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
