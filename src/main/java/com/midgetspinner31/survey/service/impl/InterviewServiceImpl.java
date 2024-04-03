package com.midgetspinner31.survey.service.impl;

import com.midgetspinner31.survey.db.dao.InterviewRepository;
import com.midgetspinner31.survey.db.dao.InterviewSlotRepository;
import com.midgetspinner31.survey.db.dao.UserRepository;
import com.midgetspinner31.survey.db.entity.Interview;
import com.midgetspinner31.survey.db.entity.InterviewSlot;
import com.midgetspinner31.survey.db.entity.userdetails.AdditionalRespondentDetails;
import com.midgetspinner31.survey.dto.InterviewInfo;
import com.midgetspinner31.survey.dto.InterviewSlotInfo;
import com.midgetspinner31.survey.exception.*;
import com.midgetspinner31.survey.factory.InterviewFactory;
import com.midgetspinner31.survey.service.InterviewService;
import com.midgetspinner31.survey.service.RespondentService;
import com.midgetspinner31.survey.web.request.InterviewRequest;
import com.midgetspinner31.survey.web.request.InterviewSlotRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service("interviewService")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InterviewServiceImpl implements InterviewService {
    UserRepository userRepository;
    InterviewRepository interviewRepository;
    InterviewSlotRepository interviewSlotRepository;
    InterviewFactory interviewFactory;

    RespondentService respondentService;

    @Override
    public InterviewInfo getInterview(String id) {
        return interviewFactory.createInterviewInfoFrom(
                interviewRepository.findById(id).orElseThrow(InterviewNotFoundException::new));
    }

    @Override
    @PreAuthorize("@surveyCreatorService.isSurveyCreator()")
    public InterviewInfo createInterview(InterviewRequest interviewRequest) {
        var user = userRepository.getCurrentUser();
        var interviewInfo = interviewFactory.createInterviewInfoFrom(user.getId(), interviewRequest);
        var interview = interviewFactory.createInterviewFrom(interviewInfo);

        var now = new Date();
        if (interview.getStartDate().after(interview.getEndDate())
                || now.after(interview.getStartDate()) || now.after(interview.getEndDate()))
            throw new InterviewInvalidTimeException();

        interview = interviewRepository.save(interview);
        return interviewFactory.createInterviewInfoFrom(interview);
    }

    @Override
    @PreAuthorize("@surveyCreatorService.isSurveyCreator()")
    public boolean isMyInterview(String interviewId) {
        var user = userRepository.getCurrentUser();
        var interview = interviewRepository.findById(interviewId).orElseThrow(InterviewNotFoundException::new);
        return Objects.equals(interview.getCreatorId(), user.getId());
    }

    @Override
    @PreAuthorize("@interviewService.isMyInterview(#interviewId)")
    public InterviewInfo updateInterview(String interviewId, InterviewRequest interviewRequest) {
        var interview = interviewRepository.getById(interviewId);

        var now = new Date();
        if (interviewRequest.getStartDate().after(interviewRequest.getEndDate())
                || now.after(interviewRequest.getStartDate()) || now.after(interviewRequest.getEndDate()))
            throw new InterviewInvalidTimeException();

        interview.setName(interviewRequest.getName());
        interview.setDescription(interviewRequest.getDescription());
        interview.setInterviewTopics(interviewRequest.getInterviewTopics());
        interview.setStartDate(interviewRequest.getStartDate());
        interview.setEndDate(interviewRequest.getEndDate());
        interview.setRespondentRestrictions(interviewRequest.getRespondentRestrictions());
        interview = interviewRepository.save(interview);
        return interviewFactory.createInterviewInfoFrom(interview);
    }

    @Override
    @PreAuthorize("@interviewService.isMyInterview(#interviewId)")
    public void deleteInterview(String interviewId) {
        var interview = interviewRepository.getById(interviewId);
        interviewRepository.delete(interview);
        interviewSlotRepository.deleteAllByInterviewId(interviewId);
    }

    @Override
    @PreAuthorize("@interviewService.isMyInterview(#interviewId)")
    public List<InterviewSlotInfo> getSlots(String interviewId) {
        var slots = interviewSlotRepository.findAllByInterviewId(interviewId);

        return slots.stream()
                .map(interviewFactory::createInterviewSlotInfoFrom)
                .toList();
    }

    @Override
    @PreAuthorize("@interviewService.isMyInterview(#interviewId)")
    public List<InterviewSlotInfo> createSlots(String interviewId, InterviewSlotRequest slotRequest) {
        var interview = interviewRepository.getById(interviewId);
        var slotInfos = interviewFactory.createInterviewSlotInfoListFrom(interviewId, slotRequest);
        var slots = slotInfos.stream().map(interviewFactory::createInterviewSlotFrom).toList();

        var now = new Date();
        for (var slot : slots) {
            if (slot.getStartDate().after(slot.getEndDate())
                    || slot.getStartDate().after(interview.getEndDate())
                    || slot.getEndDate().after(interview.getEndDate())
                    || slot.getStartDate().before(interview.getStartDate())
                    || slot.getEndDate().before(interview.getStartDate())
                    || now.after(slot.getStartDate()) || now.after(slot.getEndDate()))
                throw new InterviewSlotInvalidTimeException();
        }

        slots = interviewSlotRepository.saveAll(slots);
        return slots.stream().map(interviewFactory::createInterviewSlotInfoFrom).toList();
    }

    @Override
    public void deleteSlot(String slotId) {
        var slot = interviewSlotRepository.findById(slotId)
                .orElseThrow(InterviewSlotNotFoundException::new);
        if (!isMyInterview(slot.getInterviewId()))
            throw new AccessDeniedException("Tried to access another user's interview");

        interviewSlotRepository.delete(slot);
    }

    @Override
    public Page<InterviewInfo> getInterviewPage(Integer page, Integer size, List<String> topics) {

        AdditionalRespondentDetails details = (AdditionalRespondentDetails) userRepository.getCurrentUser().getAdditionalDetails();

        try {
            return interviewRepository.findInterviewsByInterviewTopics(topics, PageRequest.of(page, size), details)
                    .map(interviewFactory::createInterviewInfoFrom);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getStackTrace().toString());
        }
    }

    @Override
    @PreAuthorize("@surveyCreatorService.isSurveyCreator()")
    public List<InterviewInfo> getInterviewsCreatedByCurrentUser() {
        return interviewRepository
                .findAllByCreatorId(userRepository.getCurrentUser().getId())
                .stream()
                .map(interviewFactory::createInterviewInfoFrom)
                .toList();
    }

    @Override
    @PreAuthorize("@respondentService.isRespondent()")
    public List<InterviewSlotInfo> getMySlots() {
        var user = userRepository.getCurrentUser();
        var slots = interviewSlotRepository.findAllByRespondentId(user.getId());
        return slots.stream()
                .map(interviewFactory::createInterviewSlotInfoFrom)
                .toList();
    }

    @Override
    public List<InterviewSlotInfo> getFreeSlotsForInterview(String interviewId) {
        var slots = interviewSlotRepository.findAllFreeByInterviewId(interviewId);
        return slots.stream()
                .map(interviewFactory::createInterviewSlotInfoFrom)
                .toList();
    }

    @Override
    @PreAuthorize("@respondentService.isRespondent()")
    public InterviewSlotInfo acquireSlot(String slotId) {
        InterviewSlot slot = interviewSlotRepository.findById(slotId)
                .orElseThrow(InterviewSlotNotFoundException::new);

        if (slot.getStartDate().before(new Date()))
            throw new InterviewSlotEndedException();
        if (slot.getRespondentId() != null)
            throw new InterviewSlotAlreadyAcquiredException();

        Interview interview = interviewRepository.findById(slot.getInterviewId())
                .orElseThrow(InterviewNotFoundException::new);

        if (!respondentService.currentUserMatchesRestrictions(interview.getRespondentRestrictions())) {
            throw new RespondentRestrictionsNotMatchedException();
        }

        slot.setRespondentId(userRepository.getCurrentUser().getId());
        slot = interviewSlotRepository.save(slot);

        return interviewFactory.createInterviewSlotInfoFrom(slot);
    }

    @Override
    @PreAuthorize("@respondentService.isRespondent()")
    public InterviewSlotInfo releaseSlot(String slotId) {
        var user = userRepository.getCurrentUser();
        var slot = interviewSlotRepository.findById(slotId)
                .orElseThrow(InterviewSlotNotFoundException::new);

        if (slot.getStartDate().before(new Date()))
            throw new InterviewSlotEndedException();
        if (!Objects.equals(user.getId(), slot.getRespondentId()))
            throw new InterviewSlotAlreadyAcquiredException();

        slot.setRespondentId(null);
        slot = interviewSlotRepository.save(slot);

        return interviewFactory.createInterviewSlotInfoFrom(slot);
    }
}
