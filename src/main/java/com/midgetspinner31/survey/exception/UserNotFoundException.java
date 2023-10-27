package com.midgetspinner31.survey.exception;

import com.midgetspinner31.survey.constant.StatusCode;

public class UserNotFoundException extends BaseException {
    public UserNotFoundException() {
        super(StatusCode.USER_NOT_FOUND);
    }
}
