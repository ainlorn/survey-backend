package com.midgetspinner31.survey.service;

import com.midgetspinner31.survey.db.entity.RespondentRestrictions;
import com.midgetspinner31.survey.db.entity.userdetails.AdditionalRespondentDetails;

public interface RespondentService {
    boolean isRespondent();

    AdditionalRespondentDetails getRespondentDetails();

    boolean currentUserMatchesRestrictions(RespondentRestrictions restrictions);
}
