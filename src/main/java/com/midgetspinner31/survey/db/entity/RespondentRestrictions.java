package com.midgetspinner31.survey.db.entity;

import com.midgetspinner31.survey.enumerable.EducationStatus;
import com.midgetspinner31.survey.enumerable.FamilyStatus;
import com.midgetspinner31.survey.enumerable.Gender;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RespondentRestrictions {
    @Min(1)
    @Max(99)
    Integer minAge;

    @Min(1)
    @Max(99)
    Integer maxAge;

    List<@NotNull Gender> allowedGenders;

    List<@NotEmpty String> allowedRegions;

    List<@NotNull FamilyStatus> allowedFamilyStatus;

    List<@NotNull EducationStatus> allowedEducation;

    @Min(0)
    @Max(10000000)
    Integer minIncome;

    @Min(0)
    @Max(10000000)
    Integer maxIncome;

    @Min(0)
    @Max(10)
    Double minRating;
}
