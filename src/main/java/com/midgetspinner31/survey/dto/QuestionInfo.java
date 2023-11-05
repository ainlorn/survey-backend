package com.midgetspinner31.survey.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.midgetspinner31.survey.enumerable.AnswerType;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuestionInfo {
    String text;
    Boolean required;
    @JsonProperty("answer_type")
    AnswerType answerType;
    RestrictionsInfo restrictions;
}
