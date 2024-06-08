package com.midgetspinner31.survey.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.midgetspinner31.survey.db.entity.RespondentRestrictions;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SurveyInfo {
    String id;

    String name;
    String description;
    @JsonProperty("survey_topics")
    List<String> surveyTopics;

    @JsonProperty("creator_id")
    String creatorId;

    @JsonProperty("creation_date")
    Date creationDate;

    List<QuestionInfo> questions;

    RespondentRestrictions respondentRestrictions;

    Integer attemptsLeft;
}
