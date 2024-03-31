package com.midgetspinner31.survey.exception;

import com.midgetspinner31.survey.constant.StatusCode;

public class InterviewSlotEndedException extends BaseException {
    public InterviewSlotEndedException() {
        super(StatusCode.INTERVIEW_SLOT_ENDED);
    }
}
