package com.midgetspinner31.survey.web.controller;

import com.midgetspinner31.survey.service.SurveyService;
import com.midgetspinner31.survey.web.annotation.SurveyApiV1;
import com.midgetspinner31.survey.web.request.SurveyRequest;
import com.midgetspinner31.survey.web.response.SurveyResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SurveyApiV1
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SurveyController {
    SurveyService surveyService;

    @PostMapping("/surveys")
    public SurveyResponse saveSurvey(@RequestBody SurveyRequest surveyRequest) {
        return new SurveyResponse(surveyService.saveSurvey(surveyRequest));
    }

    @GetMapping("/surveys")
    public SurveyResponse getSurvey(@RequestParam String surveyId) {
        return new SurveyResponse(surveyService.getSurvey(surveyId));
    }

    @GetMapping("/survey_list")
    public List<SurveyResponse> getSurveyList() {
        return surveyService.getSurveyList().stream()
                .map(SurveyResponse::new)
                .toList();
    }

    @DeleteMapping("/surveys")
    public ResponseEntity<String> deleteSurvey(@RequestParam String surveyId) {
        return ResponseEntity.ok(surveyService.deleteSurvey(surveyId));
    }
}
