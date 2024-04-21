package com.midgetspinner31.survey.exception;

import com.midgetspinner31.survey.constant.StatusCode;

public class WalletNotFoundException extends BaseException {
    public WalletNotFoundException() {
        super(StatusCode.WALLET_NOT_FOUND);
    }
}
