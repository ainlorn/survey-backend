package com.midgetspinner31.survey.exception;

import com.midgetspinner31.survey.constant.StatusCode;

public class InterviewSlotNotFoundException extends BaseException {
    public InterviewSlotNotFoundException() {
        super(StatusCode.INTERVIEW_SLOT_NOT_FOUND);
    }
}
