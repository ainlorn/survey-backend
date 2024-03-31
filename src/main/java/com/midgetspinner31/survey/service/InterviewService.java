package com.midgetspinner31.survey.service;

import com.midgetspinner31.survey.dto.InterviewInfo;
import com.midgetspinner31.survey.dto.InterviewSlotInfo;
import com.midgetspinner31.survey.web.request.InterviewRequest;
import com.midgetspinner31.survey.web.request.InterviewSlotRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface InterviewService {
    InterviewInfo getInterview(String id);

    InterviewInfo createInterview(InterviewRequest interviewRequest);

    InterviewInfo updateInterview(String interviewId, InterviewRequest interviewRequest);

    boolean isMyInterview(String interviewId);

    void deleteInterview(String interviewId);

    List<InterviewSlotInfo> getSlots(String interviewId);

    List<InterviewSlotInfo> createSlots(String interviewId, InterviewSlotRequest slotRequest);

    void deleteSlot(String slotId);

    Page<InterviewInfo> getInterviewPage(Integer page, Integer size, List<String> topics);

    List<InterviewInfo> getInterviewsCreatedByCurrentUser();

    List<InterviewSlotInfo> getMySlots();

    List<InterviewSlotInfo> getFreeSlotsForInterview(String interviewId);

    InterviewSlotInfo acquireSlot(String slotId);

    InterviewSlotInfo releaseSlot(String slotId);
}
