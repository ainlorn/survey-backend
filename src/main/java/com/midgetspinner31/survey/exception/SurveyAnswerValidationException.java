package com.midgetspinner31.survey.exception;

import com.midgetspinner31.survey.constant.StatusCode;

public class SurveyAnswerValidationException extends BaseException {
    public SurveyAnswerValidationException() {
        super(StatusCode.SURVEY_ANSWER_VALIDATION_ERROR);
    }

    public SurveyAnswerValidationException(String message) {
        super(StatusCode.SURVEY_ANSWER_VALIDATION_ERROR, message);
    }

    public SurveyAnswerValidationException(Throwable cause) {
        super(StatusCode.SURVEY_ANSWER_VALIDATION_ERROR, cause);
    }
}
