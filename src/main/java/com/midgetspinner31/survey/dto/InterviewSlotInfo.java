package com.midgetspinner31.survey.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InterviewSlotInfo {
    String id;
    String interviewId;
    String respondentId;
    Date startDate;
    Date endDate;
}
