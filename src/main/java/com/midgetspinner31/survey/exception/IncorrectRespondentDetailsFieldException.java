package com.midgetspinner31.survey.exception;

import com.midgetspinner31.survey.constant.StatusCode;

public class IncorrectRespondentDetailsFieldException extends BaseException {

    public IncorrectRespondentDetailsFieldException() {
        super(StatusCode.RESPONDENT_FIELD_INVALID);
    }

    public IncorrectRespondentDetailsFieldException(String message) {
        super(StatusCode.RESPONDENT_FIELD_INVALID, message);
    }
}
