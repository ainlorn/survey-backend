package com.midgetspinner31.survey.service;

import java.math.BigDecimal;


public interface SurveyRewardService {

    boolean canCreateSurvey(Integer attempts, BigDecimal availableBalance);

    BigDecimal getSurveyCreationPrice(Integer attempts);

    BigDecimal getSurveyAnswerPublishPrice();
}
