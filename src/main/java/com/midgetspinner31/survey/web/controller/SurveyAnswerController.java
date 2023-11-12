package com.midgetspinner31.survey.web.controller;

import com.midgetspinner31.survey.service.SurveyAnswerService;
import com.midgetspinner31.survey.web.annotation.SurveyApiV1;
import com.midgetspinner31.survey.web.request.SurveyAnswerRequest;
import com.midgetspinner31.survey.web.response.SurveyAnswerResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@SurveyApiV1
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SurveyAnswerController {
    SurveyAnswerService surveyAnswerService;

    @PostMapping("/answer")
    public SurveyAnswerResponse saveSurveyAnswer(@RequestBody SurveyAnswerRequest surveyAnswerRequest) {
        return new SurveyAnswerResponse(surveyAnswerService.saveSurveyAnswer(surveyAnswerRequest));
    }
}
