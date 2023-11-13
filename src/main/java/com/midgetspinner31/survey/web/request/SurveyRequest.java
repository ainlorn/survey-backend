package com.midgetspinner31.survey.web.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.midgetspinner31.survey.dto.QuestionInfo;
import lombok.Value;

import java.util.List;

@Value
public class SurveyRequest {
    String name;
    String description;
    @JsonProperty("survey_topics")
    List<String> surveyTopics;
    List<QuestionInfo> questions;
}
