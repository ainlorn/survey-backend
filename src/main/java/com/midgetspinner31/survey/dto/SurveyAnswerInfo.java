package com.midgetspinner31.survey.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SurveyAnswerInfo {
    @JsonProperty("survey_id")
    String surveyId;

    @JsonProperty("answered_at")
    Date answeredAt;


    //TODO: брать id из сессии
    @JsonProperty("respondent_id")
    String respondentId;

    @JsonProperty("polling_time")
    Integer pollingTime;

    @Valid
    List<QuestionAnswerInfo> answers;
}
