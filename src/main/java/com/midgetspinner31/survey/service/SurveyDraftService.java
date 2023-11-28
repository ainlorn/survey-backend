package com.midgetspinner31.survey.service;

import com.midgetspinner31.survey.dto.SurveyInfo;
import com.midgetspinner31.survey.web.request.SurveyRequest;

public interface SurveyDraftService {
    SurveyInfo getSurveyDraft(String id);

    SurveyInfo saveSurveyDraft(SurveyRequest surveyRequest);

    String deleteSurveyDraft(String id);
}
