package com.midgetspinner31.survey.service;

import com.midgetspinner31.survey.dto.SurveyDraftInfo;
import com.midgetspinner31.survey.web.request.SurveyRequest;

public interface SurveyDraftService {
    SurveyDraftInfo getSurveyDraft(String id);

    SurveyDraftInfo saveSurveyDraft(SurveyRequest surveyRequest);

    String deleteSurveyDraft(String id);
}
