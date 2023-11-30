package com.midgetspinner31.survey.factory;

import com.midgetspinner31.survey.db.entity.User;
import com.midgetspinner31.survey.dto.UserSignUpInfo;
import com.midgetspinner31.survey.dto.UserInfo;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserFactory {
    PasswordEncoder passwordEncoder;

    public UserInfo createUserInfoFrom(User user) {
        return new UserInfo(
                user.getId(),
                user.getAccountType(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getAdditionalDetails()
        );
    }

    public User createUserFrom(UserSignUpInfo signUpInfo) {
        return User.builder()
                .email(signUpInfo.getEmail())
                .phoneNumber(signUpInfo.getPhoneNumber())
                .password(passwordEncoder.encode(signUpInfo.getPassword()))
                .accountType(signUpInfo.getAccountType())
                .additionalDetails(signUpInfo.getAdditionalDetails())
                .build();
    }
}
