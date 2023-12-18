package com.midgetspinner31.survey.db.entity.userdetails;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.midgetspinner31.survey.dto.BirthDateInfo;
import com.midgetspinner31.survey.enumerable.EducationStatus;
import com.midgetspinner31.survey.enumerable.FamilyStatus;
import com.midgetspinner31.survey.enumerable.Gender;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdditionalRespondentDetails extends AdditionalUserDetails {
    String firstName;

    String lastName;

    @JsonSerialize(converter = BirthDateInfo.BirthDateConverter.class)
    Date birthDate;

    Gender gender;

    String region;

    FamilyStatus familyStatus;

    EducationStatus educationStatus;

    Integer income;
}
