package com.midgetspinner31.survey.exception;

import com.midgetspinner31.survey.constant.StatusCode;

public class PhoneInUseException extends BaseException {
    public PhoneInUseException() {
        super(StatusCode.PHONE_IN_USE);
    }
}
