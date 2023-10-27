package com.midgetspinner31.survey.db.entity;

import com.midgetspinner31.survey.dto.UserInfo;
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

    String firstName;

    String lastName;

    String email;

    String phoneNumber;

    String password;

    public UserInfo toUserInfo() {
        return new UserInfo(
                id,
                firstName,
                lastName,
                email,
                phoneNumber
        );
    }
}
