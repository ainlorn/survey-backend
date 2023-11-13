package com.midgetspinner31.survey.exception;

import com.midgetspinner31.survey.constant.StatusCode;

public class SurveyNotFoundException extends BaseException {
    public SurveyNotFoundException() {
        super(StatusCode.SURVEY_NOT_FOUND);
    }
}
