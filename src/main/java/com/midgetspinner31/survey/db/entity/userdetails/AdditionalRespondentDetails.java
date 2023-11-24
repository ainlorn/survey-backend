package com.midgetspinner31.survey.db.entity.userdetails;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdditionalRespondentDetails extends AdditionalUserDetails {
    String firstName;
    String lastName;
}
