package com.midgetspinner31.survey.web.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.midgetspinner31.survey.db.entity.answers.QuestionAnswer;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SurveyAnswerRequest extends BaseRequest {
    @JsonProperty("polling_time")
    Integer pollingTime;

    List<JsonNode> answers;
}
