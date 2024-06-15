package com.midgetspinner31.survey.service.impl;


import com.midgetspinner31.survey.service.SurveyRewardService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class SurveyRewardServiceImpl implements SurveyRewardService {

    private static final BigDecimal ATTEMPT_PRICE = new BigDecimal(2);

    @Override
    public boolean canCreateSurvey(Integer attempts, BigDecimal availableBalance) {
        return new BigDecimal(attempts).multiply(ATTEMPT_PRICE).compareTo(availableBalance) < 1;
    }

    @Override
    public BigDecimal getSurveyCreationPrice(Integer attempts) {
        return new BigDecimal(attempts).multiply(ATTEMPT_PRICE);
    }

    @Override
    public BigDecimal getSurveyAnswerPublishPrice() {
        return ATTEMPT_PRICE;
    }
}
