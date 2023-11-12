package com.midgetspinner31.survey.web.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.midgetspinner31.survey.dto.QuestionAnswerInfo;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SurveyAnswerRequest {
    @JsonProperty("survey_id")
    String surveyId;

    //TODO: брать id из сессии
    @JsonProperty("respondent_id")
    String respondentId;

    @JsonProperty("polling_time")
    Integer pollingTime;
    List<QuestionAnswerInfo> answers;
}
