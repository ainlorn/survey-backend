package com.midgetspinner31.survey.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SurveyDraftInfo {
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
}
