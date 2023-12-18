package com.midgetspinner31.survey.service;

import com.midgetspinner31.survey.db.entity.userdetails.AdditionalUserDetails;
import com.midgetspinner31.survey.dto.UserInfo;
import com.midgetspinner31.survey.dto.UserSignUpInfo;

public interface UserService {
    UserInfo signUp(UserSignUpInfo signUpInfo);
    UserInfo getCurrentUserInfo();
    UserInfo updateCurrentUserAdditionalDetails(AdditionalUserDetails details);
}
