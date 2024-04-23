package com.midgetspinner31.survey.exception;

import com.midgetspinner31.survey.constant.StatusCode;

public class RatingNotAllowedException extends BaseException {
    public RatingNotAllowedException() {
        super(StatusCode.RATING_NOT_ALLOWED);
    }
}
