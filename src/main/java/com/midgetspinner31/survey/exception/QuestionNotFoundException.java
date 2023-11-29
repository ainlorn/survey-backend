package com.midgetspinner31.survey.exception;

import com.midgetspinner31.survey.constant.StatusCode;

public class QuestionNotFoundException extends BaseException {
    public QuestionNotFoundException() {
        super(StatusCode.QUESTION_NOT_FOUND);
    }
}
