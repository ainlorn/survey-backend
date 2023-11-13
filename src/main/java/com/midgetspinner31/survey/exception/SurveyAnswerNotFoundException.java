package com.midgetspinner31.survey.exception;

import com.midgetspinner31.survey.constant.StatusCode;

public class SurveyAnswerNotFoundException extends BaseException {
    public SurveyAnswerNotFoundException() {
        super(StatusCode.SURVEY_ANSWER_NOT_FOUND);
    }
}
