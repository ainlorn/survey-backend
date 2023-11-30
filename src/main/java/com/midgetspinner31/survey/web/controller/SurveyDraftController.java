package com.midgetspinner31.survey.web.controller;

import com.midgetspinner31.survey.dto.SurveyDraftInfo;
import com.midgetspinner31.survey.dto.SurveyInfo;
import com.midgetspinner31.survey.service.SurveyDraftService;
import com.midgetspinner31.survey.service.SurveyService;
import com.midgetspinner31.survey.web.annotation.SurveyApiV1;
import com.midgetspinner31.survey.web.request.SurveyRequest;
import com.midgetspinner31.survey.web.response.SurveyDraftResponse;
import com.midgetspinner31.survey.web.response.SurveyResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@SurveyApiV1
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SurveyDraftController {

    SurveyService surveyService;
    SurveyDraftService surveyDraftService;
    
    @PostMapping("/draft")
    public SurveyDraftResponse saveSurveyDraft(@RequestBody SurveyRequest surveyRequest) {
        return new SurveyDraftResponse(surveyDraftService.saveSurveyDraft(surveyRequest));
    }

    @GetMapping("/draft/{draftId}")
    public SurveyDraftResponse getSurvey(@PathVariable String draftId) {
        return new SurveyDraftResponse(surveyDraftService.getSurveyDraft(draftId));
    }

    @PatchMapping("/draft/{draftId}")
    public SurveyResponse convertDraftToSurvey(@PathVariable String draftId) {
        SurveyDraftInfo draft = surveyDraftService.getSurveyDraft(draftId);
        SurveyInfo surveyInfo = surveyService.saveSurvey(draft);
        return new SurveyResponse(surveyInfo);
    }
}
