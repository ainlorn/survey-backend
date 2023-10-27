package com.midgetspinner31.survey.service.impl;

import com.midgetspinner31.survey.db.dao.UserRepository;
import com.midgetspinner31.survey.db.entity.User;
import com.midgetspinner31.survey.dto.UserInfo;
import com.midgetspinner31.survey.dto.UserSignUpInfo;
import com.midgetspinner31.survey.exception.EmailInUseException;
import com.midgetspinner31.survey.exception.PhoneInUseException;
import com.midgetspinner31.survey.exception.UserNotFoundException;
import com.midgetspinner31.survey.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    PasswordEncoder passwordEncoder;
    UserRepository userRepository;

    @Override
    public UserInfo signUp(UserSignUpInfo signUpInfo) {
        if (userRepository.existsByEmail(signUpInfo.getEmail()))
            throw new EmailInUseException();
        if (userRepository.existsByPhoneNumber(signUpInfo.getPhoneNumber()))
            throw new PhoneInUseException();

        var user = User.builder()
                .firstName(signUpInfo.getFirstName())
                .lastName(signUpInfo.getLastName())
                .email(signUpInfo.getEmail())
                .phoneNumber(signUpInfo.getPhoneNumber())
                .password(passwordEncoder.encode(signUpInfo.getPassword())).build();
        user = userRepository.save(user);

        return user.toUserInfo();
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public UserInfo getCurrentUserInfo() {
        var userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(UserNotFoundException::new);

        return user.toUserInfo();
    }
}
