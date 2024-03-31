package com.midgetspinner31.survey.exception;

import com.midgetspinner31.survey.constant.StatusCode;

public class InterviewSlotInvalidTimeException extends BaseException {
    public InterviewSlotInvalidTimeException() {
        super(StatusCode.INTERVIEW_SLOT_INVALID_TIME);
    }
}
