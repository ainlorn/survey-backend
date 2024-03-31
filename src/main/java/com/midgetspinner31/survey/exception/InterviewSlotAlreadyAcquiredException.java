package com.midgetspinner31.survey.exception;

import com.midgetspinner31.survey.constant.StatusCode;

public class InterviewSlotAlreadyAcquiredException extends BaseException {
    public InterviewSlotAlreadyAcquiredException() {
        super(StatusCode.INTERVIEW_SLOT_ALREADY_ACQUIRED);
    }
}
