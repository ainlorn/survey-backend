package com.midgetspinner31.survey.web.response;

import com.midgetspinner31.survey.dto.SurveyDraftInfo;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SurveyDraftResponse extends BaseResponse {
    SurveyDraftInfo surveyInfo;
}
