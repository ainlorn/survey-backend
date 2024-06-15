package com.midgetspinner31.survey.web.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.midgetspinner31.survey.db.entity.RespondentRestrictions;
import com.midgetspinner31.survey.dto.QuestionInfo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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

    @Valid
    @NotNull
    RespondentRestrictions respondentRestrictions;

    Integer attempts;
}
