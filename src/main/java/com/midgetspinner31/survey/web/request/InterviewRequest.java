package com.midgetspinner31.survey.web.request;

import com.midgetspinner31.survey.db.entity.RespondentRestrictions;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InterviewRequest extends BaseRequest {
    @NotEmpty
    @Size(min=3, max=72)
    String name;

    @Size(min=0, max=5000)
    String description;

    @NotNull
    List<@NotEmpty @Size(min=1, max=72) String> interviewTopics;

    @NotNull
    Date startDate;

    @NotNull
    Date endDate;

    @NotNull
    RespondentRestrictions respondentRestrictions;
}
