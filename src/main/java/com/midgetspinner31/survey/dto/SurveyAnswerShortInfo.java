package com.midgetspinner31.survey.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SurveyAnswerShortInfo {
    @JsonProperty("survey_id")
    String surveyId;

    @JsonProperty("answer_id")
    String answerId;

    @JsonProperty("answered_at")
    Date answeredAt;

    @JsonProperty("respondent_id")
    String respondentId;

    @JsonProperty("polling_time")
    Integer pollingTime;
}
