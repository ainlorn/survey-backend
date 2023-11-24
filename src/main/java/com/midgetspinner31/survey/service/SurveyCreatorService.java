package com.midgetspinner31.survey.service;

import com.midgetspinner31.survey.db.entity.userdetails.AdditionalSurveyCreatorDetails;
import com.midgetspinner31.survey.dto.UserInfo;

public interface SurveyCreatorService {
    boolean isSurveyCreator();
    AdditionalSurveyCreatorDetails getSurveyCreatorDetails();
}
