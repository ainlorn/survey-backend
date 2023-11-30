package com.midgetspinner31.survey.web.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.midgetspinner31.survey.dto.QuestionInfo;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SurveyRequest extends BaseRequest {
    String name;

    String description;

    @JsonProperty("survey_topics")
    List<String> surveyTopics;

    List<QuestionInfo> questions;
}
