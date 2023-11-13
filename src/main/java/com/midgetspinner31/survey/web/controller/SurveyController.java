package com.midgetspinner31.survey.web.controller;

import com.midgetspinner31.survey.service.SurveyAnswerService;
import com.midgetspinner31.survey.service.SurveyService;
import com.midgetspinner31.survey.web.annotation.SurveyApiV1;
import com.midgetspinner31.survey.web.request.SurveyAnswerRequest;
import com.midgetspinner31.survey.web.request.SurveyRequest;
import com.midgetspinner31.survey.web.response.SurveyAnswerResponse;
import com.midgetspinner31.survey.web.response.SurveyResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.SizeLimitExceededException;
import java.util.List;

@SurveyApiV1
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SurveyController {
    SurveyService surveyService;
    SurveyAnswerService surveyAnswerService;

    @PostMapping("/surveys")
    public SurveyResponse saveSurvey(@RequestBody SurveyRequest surveyRequest) {
        return new SurveyResponse(surveyService.saveSurvey(surveyRequest));
    }

    @GetMapping("/surveys/{surveyId}")
    public SurveyResponse getSurvey(@PathVariable String surveyId) {
        return new SurveyResponse(surveyService.getSurvey(surveyId));
    }

    @GetMapping("/surveys")
    public List<SurveyResponse> getSurveyList() {
        return surveyService.getSurveyList().stream()
                .map(SurveyResponse::new)
                .toList();
    }

    @DeleteMapping("/surveys/{surveyId}")
    public ResponseEntity<String> deleteSurvey(@PathVariable String surveyId) {
        return ResponseEntity.ok(surveyService.deleteSurvey(surveyId));
    }

    @PostMapping("/surveys/{surveyId}/answers")
    public SurveyAnswerResponse saveSurveyAnswer(@PathVariable String surveyId,
                                                 @RequestBody @Valid SurveyAnswerRequest surveyAnswerRequest) {
        return new SurveyAnswerResponse(surveyAnswerService.saveSurveyAnswer(surveyId, surveyAnswerRequest));
    }

    @GetMapping("/surveys/{surveyId}/answers/{answerId}")
    public SurveyAnswerResponse getSurveyAnswer(@PathVariable String surveyId, @PathVariable String answerId) {
        return new SurveyAnswerResponse(surveyAnswerService.getSurveyAnswer(surveyId, answerId));
    }
}
