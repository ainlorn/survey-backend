package com.midgetspinner31.survey.web.response;

import com.midgetspinner31.survey.dto.SurveyInfo;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level= AccessLevel.PRIVATE)
public class SurveyResponse extends BaseResponse {
    SurveyInfo surveyInfo;
    Boolean userMeetsRestrictions;

    public SurveyResponse(SurveyInfo surveyInfo) {
        this.surveyInfo = surveyInfo;
    }
}
