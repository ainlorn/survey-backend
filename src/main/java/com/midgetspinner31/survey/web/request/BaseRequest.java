package com.midgetspinner31.survey.web.request;

public abstract class BaseRequest {
    public static String trim(String str) {
        if (str == null)
            return null;
        return str.trim();
    }
}
