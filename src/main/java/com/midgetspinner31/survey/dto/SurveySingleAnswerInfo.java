package com.midgetspinner31.survey.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.midgetspinner31.survey.db.entity.answers.QuestionAnswer;
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

    QuestionAnswer answer;
}
