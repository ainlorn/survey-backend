package com.midgetspinner31.survey.service;

import com.midgetspinner31.survey.db.entity.userdetails.AdditionalRespondentDetails;
import com.midgetspinner31.survey.dto.UserSignUpInfo;
import com.midgetspinner31.survey.dto.UserInfo;

public interface RespondentService {
    boolean isRespondent();
    AdditionalRespondentDetails getRespondentDetails();
}
