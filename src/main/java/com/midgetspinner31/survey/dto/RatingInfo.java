package com.midgetspinner31.survey.dto;

import com.midgetspinner31.survey.enumerable.InterviewResult;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RatingInfo {
    InterviewResult interviewResult;
    int rating;
    String comment;
}
