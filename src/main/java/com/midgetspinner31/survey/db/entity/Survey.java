package com.midgetspinner31.survey.db.entity;

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


    @Field("creator_id")
    String creatorId;


    @Field("creation_date")
    Date creationDate;

    /**
     * Список вопросов
     */
    List<Question> questions;

    /**
     * Ограничения для отбора респондентов
     */
    RespondentRestrictions respondentRestrictions;
}
