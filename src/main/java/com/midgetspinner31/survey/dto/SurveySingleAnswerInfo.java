package com.midgetspinner31.survey.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.midgetspinner31.survey.db.entity.answers.QuestionAnswer;
import com.midgetspinner31.survey.db.entity.userdetails.AdditionalRespondentDetails;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SurveySingleAnswerInfo {
    @JsonProperty("respondent_id")
    String respondentId;

    @JsonProperty("respondent_details")
    AdditionalRespondentDetails respondentDetails;

    QuestionAnswer answer;
}
