package com.midgetspinner31.survey.exception;

import com.midgetspinner31.survey.constant.StatusCode;

public class InterviewNotFoundException extends BaseException {
    public InterviewNotFoundException() {
        super(StatusCode.INTERVIEW_NOT_FOUND);
    }
}
