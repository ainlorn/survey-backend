package com.midgetspinner31.survey.exception;

import com.midgetspinner31.survey.constant.StatusCode;

public class LowBalanceException extends BaseException {
    public LowBalanceException() {
        super(StatusCode.WALLET_LOW_BALANCE);
    }
}
