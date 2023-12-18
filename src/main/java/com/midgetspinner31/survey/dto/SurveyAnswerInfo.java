package com.midgetspinner31.survey.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.midgetspinner31.survey.db.entity.answers.QuestionAnswer;
import com.midgetspinner31.survey.db.entity.userdetails.AdditionalRespondentDetails;
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

    @JsonProperty("answer_id")
    String answerId;

    @JsonProperty("answered_at")
    Date answeredAt;

    @JsonProperty("respondent_id")
    String respondentId;

    @JsonProperty("respondent_details")
    AdditionalRespondentDetails respondentDetails;

    @JsonProperty("polling_time")
    Integer pollingTime;

    List<QuestionAnswer> answers;
}
