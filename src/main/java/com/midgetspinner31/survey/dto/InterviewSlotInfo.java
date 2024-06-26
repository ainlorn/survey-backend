package com.midgetspinner31.survey.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InterviewSlotInfo {
    String id;
    String interviewId;
    String respondentId;
    Double respondentRating;
    Date startDate;
    Date endDate;
}
