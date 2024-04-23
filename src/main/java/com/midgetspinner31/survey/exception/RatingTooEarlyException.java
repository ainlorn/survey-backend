package com.midgetspinner31.survey.exception;

import com.midgetspinner31.survey.constant.StatusCode;

public class RatingTooEarlyException extends BaseException {
    public RatingTooEarlyException() {
        super(StatusCode.RATING_TOO_EARLY);
    }
}
