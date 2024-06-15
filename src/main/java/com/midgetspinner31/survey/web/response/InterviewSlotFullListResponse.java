package com.midgetspinner31.survey.web.response;

import com.midgetspinner31.survey.dto.InterviewSlotFullInfo;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InterviewSlotFullListResponse extends BaseResponse {
    List<InterviewSlotFullInfo> slots;
}
