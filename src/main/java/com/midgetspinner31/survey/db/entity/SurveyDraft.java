package com.midgetspinner31.survey.db.entity;

import com.midgetspinner31.survey.dto.SurveyDraftInfo;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

/**
 * Черновик опроса
 */
@Document("survey_drafts")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SurveyDraft {
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
     * id создателя
     */
    @Field("creator_id")
    String creatorId;

    /**
     * Дата создания
     */

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

    public SurveyDraftInfo toSurveyDraftInfo() {
        return new SurveyDraftInfo(
                id,
                name,
                description,
                surveyTopics,
                creatorId,
                creationDate,
                questions.stream().map(Question::toQuestionInfo).toList(),
                respondentRestrictions
        );
    }
}
