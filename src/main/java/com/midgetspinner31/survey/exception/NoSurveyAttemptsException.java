package com.midgetspinner31.survey.exception;


import com.midgetspinner31.survey.constant.StatusCode;

public class NoSurveyAttemptsException extends BaseException {

    public NoSurveyAttemptsException() {
        super(StatusCode.SURVEY_ATTEMPTS_EMPTY);
    }
}
