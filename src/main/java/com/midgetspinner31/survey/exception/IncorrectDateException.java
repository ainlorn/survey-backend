package com.midgetspinner31.survey.exception;

import com.midgetspinner31.survey.constant.StatusCode;

public class IncorrectDateException extends BaseException {
    public IncorrectDateException() {
        super(StatusCode.INCORRECT_DATE);
    }
}
