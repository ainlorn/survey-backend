package com.midgetspinner31.survey.service.impl;

import com.midgetspinner31.survey.db.dao.UserRepository;
import com.midgetspinner31.survey.db.entity.userdetails.AdditionalRespondentDetails;
import com.midgetspinner31.survey.db.entity.userdetails.AdditionalSurveyCreatorDetails;
import com.midgetspinner31.survey.db.entity.userdetails.AdditionalUserDetails;
import com.midgetspinner31.survey.dto.UserInfo;
import com.midgetspinner31.survey.dto.UserSignUpInfo;
import com.midgetspinner31.survey.enumerable.AccountType;
import com.midgetspinner31.survey.exception.EmailInUseException;
import com.midgetspinner31.survey.exception.PhoneInUseException;
import com.midgetspinner31.survey.exception.UserNotFoundException;
import com.midgetspinner31.survey.factory.UserFactory;
import com.midgetspinner31.survey.service.UserService;
import com.midgetspinner31.survey.service.WalletService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    UserFactory userFactory;
    WalletService walletService;

    @Override
    public UserInfo signUp(UserSignUpInfo signUpInfo) {
        if (userRepository.existsByEmail(signUpInfo.getEmail()))
            throw new EmailInUseException();
        if (userRepository.existsByPhoneNumber(signUpInfo.getPhoneNumber()))
            throw new PhoneInUseException();

        var user = userRepository.save(userFactory.createUserFrom(signUpInfo));
        walletService.createWalletForUser(user.getId());

        return userFactory.createUserInfoFrom(user);
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public UserInfo getCurrentUserInfo() {
        var userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(UserNotFoundException::new);

        return userFactory.createUserInfoFrom(user);
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public UserInfo updateCurrentUserAdditionalDetails(AdditionalUserDetails details) {
        var user = userRepository.getCurrentUser();
        if (details instanceof AdditionalRespondentDetails && user.getAccountType() == AccountType.respondent
                || details instanceof AdditionalSurveyCreatorDetails && user.getAccountType() == AccountType.survey_creator) {
            user.setAdditionalDetails(details);
        } else {
            throw new AccessDeniedException("Wrong user account type!");
        }

        user = userRepository.save(user);
        return userFactory.createUserInfoFrom(user);
    }
}
