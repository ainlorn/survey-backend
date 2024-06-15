package com.midgetspinner31.survey.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InterviewSlotFullInfo {
    String slotId;
    String interviewId;
    InterviewInfo interview;
    String respondentId;
    Double respondentRating;
    Date startDate;
    Date endDate;
}
