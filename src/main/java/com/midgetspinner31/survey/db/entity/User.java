package com.midgetspinner31.survey.db.entity;

import com.midgetspinner31.survey.db.entity.userdetails.AdditionalUserDetails;
import com.midgetspinner31.survey.enumerable.AccountType;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    String id;

    AccountType accountType;

    String email;

    String phoneNumber;

    String password;

    AdditionalUserDetails additionalDetails;
}
