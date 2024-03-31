package com.midgetspinner31.survey.dto;

import com.midgetspinner31.survey.db.entity.RespondentRestrictions;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InterviewInfo {
    String id;
    String name;
    String description;
    List<String> interviewTopics;
    String creatorId;
    Date creationDate;
    Date startDate;
    Date endDate;
    RespondentRestrictions respondentRestrictions;
}
