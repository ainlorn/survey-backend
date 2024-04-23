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
public class UserInfo {
    String id;
    AccountType accountType;
    String email;
    String phoneNumber;
    AdditionalUserDetails additionalDetails;
    Double rating;
}
