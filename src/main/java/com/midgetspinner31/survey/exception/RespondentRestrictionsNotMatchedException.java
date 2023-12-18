package com.midgetspinner31.survey.exception;

import com.midgetspinner31.survey.constant.StatusCode;

public class RespondentRestrictionsNotMatchedException extends BaseException {
    public RespondentRestrictionsNotMatchedException() {
        super(StatusCode.RESPONDENT_RESTRICTIONS_NOT_MATCHED);
    }
}
