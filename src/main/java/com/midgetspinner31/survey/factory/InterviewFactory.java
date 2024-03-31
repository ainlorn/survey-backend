package com.midgetspinner31.survey.factory;

import com.midgetspinner31.survey.db.entity.Interview;
import com.midgetspinner31.survey.db.entity.InterviewSlot;
import com.midgetspinner31.survey.dto.InterviewInfo;
import com.midgetspinner31.survey.dto.InterviewSlotInfo;
import com.midgetspinner31.survey.web.request.InterviewRequest;
import com.midgetspinner31.survey.web.request.InterviewSlotRequest;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class InterviewFactory {
    public Interview createInterviewFrom(InterviewInfo interviewInfo) {
        return Interview.builder()
                .name(interviewInfo.getName())
                .description(interviewInfo.getDescription())
                .interviewTopics(interviewInfo.getInterviewTopics())
                .creatorId(interviewInfo.getCreatorId())
                .creationDate(interviewInfo.getCreationDate())
                .startDate(interviewInfo.getStartDate())
                .endDate(interviewInfo.getEndDate())
                .respondentRestrictions(interviewInfo.getRespondentRestrictions())
                .build();
    }

    public InterviewInfo createInterviewInfoFrom(Interview interview) {
        return new InterviewInfo(
                interview.getId(),
                interview.getName(),
                interview.getDescription(),
                interview.getInterviewTopics(),
                interview.getCreatorId(),
                interview.getCreationDate(),
                interview.getStartDate(),
                interview.getEndDate(),
                interview.getRespondentRestrictions()
        );
    }

    public InterviewInfo createInterviewInfoFrom(String creatorId, InterviewRequest interviewRequest) {
        return new InterviewInfo(
                null,
                interviewRequest.getName(),
                interviewRequest.getDescription(),
                interviewRequest.getInterviewTopics(),
                creatorId,
                new Date(),
                interviewRequest.getStartDate(),
                interviewRequest.getEndDate(),
                interviewRequest.getRespondentRestrictions()
        );
    }

    public InterviewSlot createInterviewSlotFrom(InterviewSlotInfo interviewSlotInfo) {
        return InterviewSlot.builder()
                .interviewId(interviewSlotInfo.getInterviewId())
                .respondentId(interviewSlotInfo.getRespondentId())
                .startDate(interviewSlotInfo.getStartDate())
                .endDate(interviewSlotInfo.getEndDate())
                .build();
    }

    public InterviewSlotInfo createInterviewSlotInfoFrom(InterviewSlot interviewSlot) {
        return new InterviewSlotInfo(
                interviewSlot.getId(),
                interviewSlot.getInterviewId(),
                interviewSlot.getRespondentId(),
                interviewSlot.getStartDate(),
                interviewSlot.getEndDate()
        );
    }

    public List<InterviewSlotInfo> createInterviewSlotInfoListFrom(String interviewId, InterviewSlotRequest interviewSlot) {
        return interviewSlot.getSlots()
                .stream()
                .map(x -> createInterviewSlotInfoFrom(interviewId, x))
                .toList();
    }

    public InterviewSlotInfo createInterviewSlotInfoFrom(String interviewId, InterviewSlotRequest.SlotItem slotItem) {
        return new InterviewSlotInfo(
                null,
                interviewId,
                null,
                slotItem.getStartDate(),
                slotItem.getEndDate()
        );
    }
}
