package com.midgetspinner31.survey.exception;

import com.midgetspinner31.survey.constant.StatusCode;

public class RatingNotFoundException extends BaseException {
    public RatingNotFoundException() {
        super(StatusCode.RATING_NOT_FOUND);
    }
}
