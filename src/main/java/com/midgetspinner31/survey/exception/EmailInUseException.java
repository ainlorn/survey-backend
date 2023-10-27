package com.midgetspinner31.survey.exception;

import com.midgetspinner31.survey.constant.StatusCode;

public class EmailInUseException extends BaseException {
    public EmailInUseException() {
        super(StatusCode.EMAIL_IN_USE);
    }
}
