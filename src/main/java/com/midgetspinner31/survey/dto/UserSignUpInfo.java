package com.midgetspinner31.survey.dto;

import com.midgetspinner31.survey.db.entity.userdetails.AdditionalUserDetails;
import com.midgetspinner31.survey.enumerable.AccountType;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserSignUpInfo {
    AccountType accountType;
    String email;
    String phoneNumber;
    String password;
    AdditionalUserDetails additionalDetails;
}
