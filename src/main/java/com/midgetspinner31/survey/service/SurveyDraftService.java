package com.midgetspinner31.survey.service;

import com.midgetspinner31.survey.dto.SurveyDraftInfo;
import com.midgetspinner31.survey.web.request.SurveyRequest;

import java.util.List;

public interface SurveyDraftService {

    List<SurveyDraftInfo> getAllSurveyDrafts();

    SurveyDraftInfo getSurveyDraft(String id);

    SurveyDraftInfo saveSurveyDraft(SurveyRequest surveyRequest);

    String deleteSurveyDraft(String id);
}
