package com.midgetspinner31.survey.exception;

import com.midgetspinner31.survey.constant.StatusCode;

public class InterviewInvalidTimeException extends BaseException {
    public InterviewInvalidTimeException() {
        super(StatusCode.INTERVIEW_INVALID_TIME);
    }
}
